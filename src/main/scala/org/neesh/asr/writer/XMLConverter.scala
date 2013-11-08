package com.sabre.apd.loyalty.batch.asr.writer

import com.sabre.apd.loyalty.batch.asr.beans.{TKTDocIdentification, TX}

/**
 * Created by IntelliJ IDEA.
 * User: SG0209008
 * Date: 4/24/11
 * Time: 11:27 PM
 * To change this template use File | Settings | File Templates.
 */

object XMLConverter {
  def convertTXToXMLString(tx: TX): String = {
    tx.stationHeader.code
    val template = <ConfirmRQ>
      <StationNumber>{tx.stationHeader.stationNumber}</StationNumber>
      <StationCode>{tx.stationHeader.stationNumber}</StationCode>
      <TicketNumber>{getTKTNumber(tx.docInfo)}</TicketNumber>
      <PNR>{tx.docInfo(0).pnrNumber}</PNR>
      <Amount>149.80</Amount>
      <Pax>
        <Name>{tx.pax.name}</Name>
      </Pax>
      <TotalTicketPrice>149.80</TotalTicketPrice>
      <BaseFare>117.00</BaseFare>
      <Tax>32.80</Tax>
      <Segments>
        <Segment>
          <SegNumber>1</SegNumber>
          <CouponUseIndicator>F</CouponUseIndicator>
          <Origin>YYC</Origin>
          <Destination>YVR</Destination>
          <FlightNumber>WS0547</FlightNumber>
          <Date>20OCT2009</Date>
          <Clas>G</Clas>
          <StopoverIndicator>X</StopoverIndicator>
          <Fare>GBRUVOPL</Fare>
        </Segment>
      </Segments>
      <TourCode>IBM03</TourCode>
      <Taxes>
        <TaxDetail>
          <Type>CA</Type>
          <Amount>4.67</Amount>
        </TaxDetail>
        <TaxDetail>
          <Type>XG</Type>
          <Amount>7.13</Amount>
        </TaxDetail>
        <TaxDetail>
          <Type>SQ</Type>
          <Amount>21.00</Amount>
        </TaxDetail>
      </Taxes>
      <FareCalculation>YYC WS YVR Q15.00Q3.00 99.00CAD117.00END</FareCalculation>
    </ConfirmRQ>

    template.text
  }

  private def getTKTNumber(docInfo: List[TKTDocIdentification]): String = {
    // TODO get TKT# based on on doctype
    docInfo(0).docNumber
  }
}