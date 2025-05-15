package com.leadlink.backend.service;

import com.leadlink.backend.dto.InvoiceRequestDTO;
import com.leadlink.backend.dto.InvoiceDTO;
import com.leadlink.backend.dto.PreFillInvoiceData;
import com.leadlink.backend.exception.InvoiceNotFoundException;
import com.leadlink.backend.model.*;
import com.leadlink.backend.repository.*;
import com.leadlink.backend.security.UserPrincipal;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ContactCaseRepository contactCaseRepository;
    private final UserRepository userRepository;
    private final CaseRepository caseRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, ContactCaseRepository contactCaseRepository, UserRepository userRepository, CaseRepository caseRepository) {
        this.invoiceRepository = invoiceRepository;
        this.contactCaseRepository = contactCaseRepository;
        this.userRepository = userRepository;
        this.caseRepository = caseRepository;
    }



    public Invoice createInvoiceFromCase(InvoiceRequestDTO request, Cases selectedCase) {
        Users currentUser = getCurrentUser();

        ContactCase contactCase = contactCaseRepository.findByCasesId(selectedCase.getId())
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Kontakt pro případ nebyl nalezen."));

        Invoice invoice = new Invoice();
        invoice.setUser(currentUser);
        invoice.setCases(selectedCase);
        invoice.setContact(contactCase.getContact());
        invoice.setIssueDate(request.getIssueDate());
        invoice.setDueDate(request.getDueDate());
        invoice.setAmount(request.getAmount());
        invoice.setDescription(request.getDescription());
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setStatus(InvoiceStatus.ISSUED);

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException(id));
    }

    public Invoice updateInvoice(Long id, InvoiceRequestDTO dto) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException(id));

        invoice.setDescription(dto.getDescription());
        invoice.setAmount(dto.getAmount());
        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setIssueDate(dto.getIssueDate());
        invoice.setDueDate(dto.getDueDate());
        invoice.setStatus(dto.getStatus());

        return invoiceRepository.save(invoice);
    }


    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new InvoiceNotFoundException(id);
        }
        invoiceRepository.deleteById(id);
    }

    public List<Invoice> getInvoicesForCurrentUser() {
        Users currentUser = getCurrentUser();
        return invoiceRepository.findByUser_Id(currentUser.getId());
    }

    public PreFillInvoiceData getPreFillInvoiceData(Long caseId) {
        Cases cases = selectedCaseById(caseId);
        ContactCase contactCase = contactCaseRepository.findByCasesId(caseId)
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Kontakt k případu nenalezen."));

        PreFillInvoiceData data = new PreFillInvoiceData();
        data.setCaseName(cases.getName());
        data.setAmount(cases.getPrice());
        data.setClientName(contactCase.getContact().getFirstname() + " " + contactCase.getContact().getLastname());
        data.setClientEmail(contactCase.getContact().getEmail());
        data.setUserFullName(cases.getUser().getFirstname() + " " + cases.getUser().getLastname());
        data.setUserEmail(cases.getUser().getEmail());
        return data;
    }

    private Users getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Uživatel nebyl nalezen");
        }
        return user;
    }


    private Cases selectedCaseById(Long caseId) {
        return caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Případ nenalezen"));
    }


    public byte[] generateInvoicePdf(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

// Nadpis
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("FAKTURA", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

// Základní info
        Font normal = new Font(Font.HELVETICA, 12);
        document.add(new Paragraph("Faktura č.: " + invoice.getInvoiceNumber(), normal));
        document.add(new Paragraph("Datum vystavení: " + invoice.getIssueDate(), normal));
        document.add(new Paragraph("Datum splatnosti: " + invoice.getDueDate(), normal));
        document.add(Chunk.NEWLINE);

// --- Údaje o dodavateli ---
        document.add(new Paragraph("Dodavatel:", normal));
        document.add(new Paragraph("Název / Jméno: Martina Kvasničková", normal));
        document.add(new Paragraph("Adresa: Pardubice I - Bílé Předměstí, Gebauerova 222", normal));
        document.add(new Paragraph("IČO: 12345678", normal));
        document.add(new Paragraph("DIČ: ....................", normal));
        document.add(Chunk.NEWLINE);

// --- Údaje o odběrateli ---
        document.add(new Paragraph("Odběratel:", normal));
        document.add(new Paragraph("Název / Jméno: " + invoice.getContact().getFirstname() + " " + invoice.getContact().getLastname(), normal));
        document.add(new Paragraph("Adresa: Praha 2, Husova 18", normal));
        document.add(new Paragraph("IČ: 23456789", normal));
        document.add(new Paragraph("DIČ: ....................", normal));
        document.add(Chunk.NEWLINE);

// Tabulka s fakturačními údaji
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

// Hlavičky
        Stream.of("Položka", "Hodnota").forEach(header -> {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setPadding(5);
            table.addCell(cell);
        });

// Data
        table.addCell("Popis");
        table.addCell(invoice.getDescription());

// Předvyplněné info z backendu
        table.addCell("Klient");
        table.addCell(invoice.getContact().getFirstname() + " " + invoice.getContact().getLastname());

        table.addCell("Vystavil");
        table.addCell(invoice.getUser().getFirstname() + " " + invoice.getUser().getLastname());


// Připravené chlívečky pro sazbu a DPH

        float amount = invoice.getAmount();
        float dphRate = 0.21f;
        float dph = (float) Math.round(amount * dphRate * 100) / 100;
        float totalWithDph = (float) Math.round((amount + dph) * 100) / 100;

        table.addCell("Základ daně");
        table.addCell(amount + " Kč");

        table.addCell("Sazba DPH");
        table.addCell("21 %");

        table.addCell("DPH");
        table.addCell(dph + " Kč");

        table.addCell("Celkem vč. DPH");
        table.addCell(totalWithDph + " Kč");

        document.add(table);

// --- Údaje k platbě ---
        document.add(new Paragraph("Bankovní spojení:", normal));
        document.add(new Paragraph("Číslo účtu: 123443215678 / 2222", normal));
        document.add(new Paragraph("Variabilní symbol: 2024-04", normal));
        document.add(Chunk.NEWLINE);

// Podpisová poznámka
        document.add(new Paragraph("Děkujeme za spolupráci.", normal));

        document.close();
        return baos.toByteArray();
    }
}
