import React from 'react'

import '../../App.css';
import './css/Layout.css';

import DashboardImg from '../../assets/images/contacts.png'
import CalendarImg from '../../assets/images/calendar.png'
import CasesImg from '../../assets/images/cases.png'
import InvoicesImg from '../../assets/images/invoices.png'


export default function Manual() {
  return (
    <div className="manual-content">

    <div className='manual-block'>
      <h1 className="text-1xl font-bold mb-6">Uživatelská příručka – LeadLink CRM</h1>

      <section className="mb-8">
        <h2 className="text-xl font-semibold mb-2 mt-4">1. Úvod</h2>
        <p>
          Aplikace LeadLink CRM je navržena pro freelancery a OSVČ, kteří potřebují efektivně spravovat své klienty,
          obchodní případy, kalendář a fakturaci. Tento návod vás provede základními funkcemi systému.
        </p>
      </section>

      <section className="mb-8">
        <h2 className="text-xl mb-2 mt-4">2. Přihlášení a registrace</h2>
        <ul className="list-disc pl-6">
          <li>Na přihlašovací stránce klikněte na <strong>Registrovat</strong>.</li>
          <li>Vyplňte jméno, e-mail, uživatelské jméno a heslo.</li>
          <li>Po úspěšné registraci se přihlaste pomocí zvolených údajů.</li>
        </ul>
      </section>

      <section className="mb-8">
        <h2 className="text-xl mb-2 mt-4">3. Přidání klienta</h2>
        <ul className="list-disc pl-6">
          <li>Přejděte do sekce <strong>Adresář</strong>.</li>
          <li>Klikněte na tlačítko <strong>+</strong> pro přidání nového kontaktu.</li>
          <li>Vyplňte jméno, příjmení, e-mail (volitelně další info) a uložte.</li>

          <div className='manual-image'>
            <img src={DashboardImg} alt="Ukázka rozhraní" />
          </div>
        </ul>
      </section>

      <section className="mb-8">
        <h2 className="text-xl mb-2 mt-4">4. Vytvoření obchodního případu</h2>
        <ul className="list-disc pl-6">
          <li>Přejděte do sekce <strong>Případy</strong>.</li>
          <li>Klikněte na <strong>+</strong> a zadejte název a cenu zakázky.</li>
          <li>Případ můžete propojit s klientem kliknutím na ikonu propojení.</li>
          <div className='manual-image'>
            <img src={CasesImg} alt="Ukázka rozhraní" />
          </div>
        </ul>
      </section>

      <section className="mb-8">
        <h2 className="text-xl mb-2 mt-4">5. Kalendář a události</h2>
        <ul className="list-disc pl-6">
          <li>Přejděte do sekce <strong>Kalendář</strong>.</li>
          <li>Pro přidání nové události klikněte na <strong>+</strong>.</li>
          <li>Vyplňte název, datum a případně propojte s obchodním případem.</li>
          <div className='manual-image'>
            <img src={CalendarImg} alt="Ukázka rozhraní" />
          </div>
        </ul>
      </section>

      <section className="mb-8">
        <h2 className="text-xl mb-2 mt-4">6. Vystavení faktury</h2>
        <ul className="list-disc pl-6">
          <li>Přejděte do sekce <strong>Fakturace</strong>.</li>
          <li>Klikněte na <strong>+</strong> pro generování faktury.</li>
          <li>Vyberte obchodní případ – data se předvyplní automaticky.</li>
          <li>Zadejte datum vystavení a splatnosti a klikněte na <strong>Uložit</strong>.</li>
          <div className='manual-image'>
            <img src={InvoicesImg} alt="Ukázka rozhraní" />
          </div>
        </ul>
      </section>

      <section className="mb-8">
        <h2 className="text-xl mb-2 mt-4">7. Změna stavu faktury</h2>
        <ul className="list-disc pl-6">
          <li>Ve fakturační sekci klikněte na menu u faktury (např. tři tečky).</li>
          <li>Vyberte nový stav: <em>ISSUED</em>, <em>PAID</em>, nebo <em>CANCELLED</em>.</li>
          <div className='manual-image'>
            <img src={InvoicesImg} alt="Ukázka rozhraní" />
          </div>
        </ul>
      </section>

      <section className="mb-8">
        <h2 className="text-xl mb-2 mt-4">8. Časté dotazy (FAQ)</h2>
        <p className="mb-2 font-medium">❓ Můžu vytvořit více faktur k jednomu případu?</p>
        <p className="mb-4">Ano, ale doporučuje se evidovat je odděleně – např. rozdělením případu na etapy.</p>

        <p className="mb-2 font-medium">❓ Je možné propojit více klientů k jednomu případu?</p>
        <p>Ano, ale vždy je primárně zobrazen pouze jeden hlavní kontakt.</p>
      </section>

      <section>
        <h2 className="text-xl mb-2 mt-4">9. Kontakt na podporu</h2>
        <p>Pokud narazíte na problém nebo potřebujete poradit, kontaktujte nás na: <strong>support@leadlink.cz</strong></p>
      </section>
      </div>
    </div>
  )
}
