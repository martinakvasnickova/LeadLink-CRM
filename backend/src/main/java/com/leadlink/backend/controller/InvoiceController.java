package com.leadlink.backend.controller;

import com.leadlink.backend.dto.InvoiceRequestDTO;
import com.leadlink.backend.dto.InvoiceDTO;
import com.leadlink.backend.dto.PreFillInvoiceData;
import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Invoice;
import com.leadlink.backend.service.CaseService;
import com.leadlink.backend.service.InvoiceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;




@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    private final InvoiceService invoiceService;
    private final CaseService caseService;

    public InvoiceController(InvoiceService invoiceService, CaseService caseService) {
        this.invoiceService = invoiceService;
        this.caseService = caseService;
    }

    @PostMapping
    public InvoiceDTO newInvoice(@RequestBody @Valid InvoiceRequestDTO request) {
        logger.info("Vytvářím novou fakturu pro případ ID: {}", request.getCaseId());

        Cases selectedCase = caseService.getCaseById(request.getCaseId());
        Invoice createdInvoice = invoiceService.createInvoiceFromCase(request, selectedCase);

        return mapToDTO(createdInvoice);
    }

    @GetMapping
    public List<InvoiceDTO> getAllInvoices() {
        logger.info("Načítám všechny faktury");
        return invoiceService.getAllInvoices().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public InvoiceDTO getInvoiceById(@PathVariable Long id) {
        logger.info("Načítám fakturu s ID {}", id);
        return mapToDTO(invoiceService.getInvoiceById(id));
    }

    @DeleteMapping("/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        logger.warn("Mažu fakturu s ID {}", id);
        invoiceService.deleteInvoice(id);
        return "Faktura s ID " + id + " byla úspěšně smazána.";
    }

    @PutMapping("/{id}")
    public InvoiceDTO updateInvoice(@PathVariable Long id, @RequestBody InvoiceRequestDTO invoiceRequestDTO) {
        logger.info("Aktualizuji fakturu s ID {}", id);
        Invoice updated = invoiceService.updateInvoice(id, invoiceRequestDTO);
        return mapToDTO(updated);
    }


    @GetMapping("/user")
    public List<InvoiceDTO> getInvoicesForCurrentUser() {
        logger.info("Načítám faktury pro aktuálního uživatele");
        return invoiceService.getInvoicesForCurrentUser().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pre-fill")
    public PreFillInvoiceData getPreFillData(@RequestParam Long caseId) {
        logger.info("Načítám předvyplněná data pro případ ID {}", caseId);
        return invoiceService.getPreFillInvoiceData(caseId);
    }

    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long id) {
        logger.info("Generuji PDF pro fakturu ID {}", id);
        byte[] pdfBytes = invoiceService.generateInvoicePdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "faktura_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    private InvoiceDTO mapToDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        System.out.println("DEBUG: Stav faktury = " + invoice.getStatus());
        dto.setId(invoice.getId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setAmount(invoice.getAmount());
        dto.setDescription(invoice.getDescription());
        dto.setStatus(invoice.getStatus().toString());
        dto.setIssueDate(invoice.getIssueDate());
        dto.setDueDate(invoice.getDueDate());
        dto.setCaseName(invoice.getCases() != null ? invoice.getCases().getName() : null);
        dto.setClientName(invoice.getContact() != null
                ? invoice.getContact().getFirstname() + " " + invoice.getContact().getLastname()
                : null);
        dto.setUserName(invoice.getUser() != null
                ? invoice.getUser().getFirstname() + " " + invoice.getUser().getLastname()
                : null);
        return dto;
    }

}
