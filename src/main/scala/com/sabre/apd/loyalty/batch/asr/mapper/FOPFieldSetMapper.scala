package com.sabre.apd.loyalty.batch.asr.mapper

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import com.sabre.apd.loyalty.batch.asr.beans.{RecordType, FOP}

/**
 * Created by IntelliJ IDEA.
 * User: SG0209008
 * Date: 4/6/11
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */

class FOPFieldSetMapper extends FieldSetMapper[FOP] with FieldSanitizer{
  def mapFieldSet(fs : FieldSet) : FOP = {

    val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))

    return new FOP(recordType, fs.readString("paymentType"),
                    sanitize(fs.readString("amount")),
                    fs.readString("accountNumber"),
                    fs.readString("expiryDate"),
                    fs.readString("approvalCode"),
                    fs.readString("invoiceNumber"),
                    fs.readString("invoiceDate"),
                    fs.readString("description"))
  }

}

object FOPFields extends Enumeration{

}