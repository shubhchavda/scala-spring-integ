package com.sabre.apd.loyalty.batch.asr.beans

import reflect.BeanProperty
import xml.Elem

// BOH03
class StationHeader(val recordType: RecordType, val stationNumber: String, val saleDate: String, val code: String, val currency: String) {
  def toXML: Elem = {
    <StationHeader>
      <StationNumber>{stationNumber}</StationNumber>
      <SaleDate>{saleDate}</SaleDate>
      <StationCode>{code}</StationCode>
      <Currency>{currency}</Currency>
    </StationHeader>
  }
}

case class RecordType(val id: String, val qualifier: String) {

}

class Commission(val recordType: RecordType,
                 val docNumber: String,
                 val rate: String,
                 val amount: String,
                 val suppType: String,
                 val suppRate: String,
                 val suppAmount: String,
                 val effectiveAmount: String) {
  def toXML: Elem = {
    <Commission>
      <Rate>{rate}</Rate>
      <Amount>{amount}</Amount>
      <SuppType>{suppType}</SuppType>
      <SuppAmount>{suppAmount}</SuppAmount>
      <SuppRate>{suppRate}</SuppRate>
      <EffectiveAmount>{effectiveAmount}</EffectiveAmount>
    </Commission>
  }
}

class TX(val stationHeader: StationHeader,
         val txHeader: TXHeader,
         val docInfo: List[TKTDocIdentification],
         val stdDocAmounts: List[StandardDocAmounts],
         val docAmounts: DocAmounts,
         val commissions: List[Commission],
         val itinerary: List[Itinerary],
         val misc: List[Misc],
         val pax: PAX,
         val fareCalculation: List[FareCalculation],
         val fop: List[FOP]
          ) {

  def toXML: Elem = {
    <Transaction>
      {stationHeader.toXML}
      {txHeader.toXML}
      <DocIdentifications>
        {for (doc <- docInfo) yield doc.toXML}
      </DocIdentifications>
      <StdDocAmounts>
        {for (stdDocAmt <- stdDocAmounts) yield stdDocAmt.toXML}
      </StdDocAmounts>
      <Commissions>
        {for (comm <- commissions) yield comm.toXML}
      </Commissions>
      <Itineraries>
        {for (itin <- itinerary) yield itin.toXML}
      </Itineraries>
      {docAmounts.toXML}
      {pax.toXML}
      <Miscs>
        {for (m <- misc) yield m.toXML}
      </Miscs>
      <FareCalculations>
        {for (f <- fareCalculation) yield f.toXML}
      </FareCalculations>
      <FOPs>
        {for (f <- fop) yield f.toXML}
      </FOPs>
    </Transaction>
  }
}

// BKT06
class TXHeader(val recordType: RecordType, val docType: String, val eTKT: String, val tranRefNumber: String) {
  def toXML: Elem = {
    <TransactionHeader>
      <DocType>{docType}</DocType>
      <ETKT>{eTKT}</ETKT>
      <TransRefNumber>{tranRefNumber}</TransRefNumber>
    </TransactionHeader>
  }
}


class TKTDocIdentification(val recordType: RecordType,
                           val docNumber: String,
                           val couponIndicator: String,
                           val conjTktIndicator: String,
                           val tourCode: String,
                           val txnCode: String,
                           val pnrNumber: String
                            ) {

  def toXML: Elem = {
    <DocIdentification>
      <DocNumber>{docNumber}</DocNumber>
      <CouponIndicator>{couponIndicator}</CouponIndicator>
      <ConjTicketIndicator>{conjTktIndicator}</ConjTicketIndicator>
      <TourCode>{tourCode}</TourCode>
      <TransactionCode>{txnCode}</TransactionCode>
      <PNR>{pnrNumber}</PNR>
    </DocIdentification>
  }

}

class StandardDocAmounts(val recordType: RecordType,
                         val baseFare: String,
                         val netFare: String,
                         val taxCode1: String,
                         val taxAmount1: String,
                         val taxCode2: String,
                         val taxAmount2: String,
                         val currencyType: String
                          ) {

  def toXML: Elem = {
    <StdDocAmount>
      <BaseFare>{baseFare}</BaseFare>
      <NetFare>{netFare}</NetFare>
      <TaxCode1>{taxCode1}</TaxCode1>
      <TaxAmount1>{taxAmount1}</TaxAmount1>
      <TaxCode2>{taxCode2}</TaxCode2>
      <TaxAmount2>{taxAmount2}</TaxAmount2>
      <Currency>{currencyType}</Currency>
    </StdDocAmount>

  }

}

class DocAmounts(val recordType: RecordType,
                 val fare: String,
                 val equivalentFare: String,
                 val fareCurrency: String,
                 val equivalentFareCurrency: String
                  ) {

  def toXML: Elem = {
    <DocAmounts>
      <Fare>{fare}</Fare>
      <EquivalentFare>{equivalentFare}</EquivalentFare>
      <FareCurrency>{fareCurrency}</FareCurrency>
      <EquivalentFareCurrency>{equivalentFareCurrency}</EquivalentFareCurrency>
    </DocAmounts>
  }

}

class FareCalculation(val recordType: RecordType, val text: String) {

  def toXML: Elem = {
    <FareCalculation>{text}</FareCalculation>
  }

}

class FOP(val recordType: RecordType,
          val paymentType: String,
          val amount: String,
          val accountNumber: String,
          val expiryDate: String,
          val approvalCode: String,
          val invoiceNumber: String,
          val invoiceDate: String,
          val description: String
           ) {
  def toXML: Elem = {
    <FOP>
      <PaymentType>{paymentType}</PaymentType>
      <Amount>{amount}</Amount>
      <AccountNumber>{accountNumber}</AccountNumber>
      <ExpiryDate>{expiryDate}</ExpiryDate>
      <ApprovalCode>{approvalCode}</ApprovalCode>
      <InvoiceNumber>{invoiceNumber}</InvoiceNumber>
      <InvoiceDate>{invoiceDate}</InvoiceDate>
      <Description>{description}</Description>
    </FOP>
  }
}

class Itinerary(val recordType: RecordType,
                val issueDate: String,
                val ticketDocNumber: String,
                val ticketDocNumberCheckDigit: String,
                val segmentIdentifier: String,
                val stopOverCode: String,
                val origin: String,
                val destination: String,
                val carrier: String,
                val flightNumber: String,
                val classOfService: String,
                val flightDate: String,
                val fareBasis: String
                 ) {

  def toXML: Elem = {
    <Itinerary>
      <TKTDocNumber>{ticketDocNumber}</TKTDocNumber>
      <SegmentIdentifier>{segmentIdentifier}</SegmentIdentifier>
      <StopOverCode>{stopOverCode}</StopOverCode>
      <Origin>{origin}</Origin>
      <Destination>{destination}</Destination>
      <Carrier>{carrier}</Carrier>
      <FlightNumber>{flightNumber}</FlightNumber>
      <ClassOfService>{classOfService}</ClassOfService>
      <FlightDate>{flightDate}</FlightDate>
      <FareBasis>{fareBasis}</FareBasis>
      <IssueDate>{issueDate}</IssueDate>
    </Itinerary>
  }

}

class Misc(val recordType: RecordType, val prasCode: String, val description: String, val quantity: Int, val price: Double) {

  def toXML: Elem = {
    <Misc>
      <Code>{prasCode}</Code>
      <Description>{description}</Description>
      <Quantity>{quantity}</Quantity>
      <Price>{price}</Price>
    </Misc>
  }

}

class PAX(val recordType: RecordType, val name: String) {
  def toXML: Elem = {
    <Pax>{name}</Pax>
  }
}

class RelatedDocInfo {

}

class Transactions(val txns : List[TX])
