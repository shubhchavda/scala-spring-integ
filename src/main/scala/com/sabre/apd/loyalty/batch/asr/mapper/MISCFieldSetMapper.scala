package com.sabre.apd.loyalty.batch.asr.mapper

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import com.sabre.apd.loyalty.batch.asr.beans.{RecordType, Misc}

/**
 * Created by IntelliJ IDEA.
 * User: SG0209008
 * Date: 4/6/11
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */

class MISCFieldSetMapper extends FieldSetMapper[Misc]{
  def mapFieldSet(fs : FieldSet) : Misc = {
    val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))

    return new Misc(recordType, fs.readString("prasCode"),
                    fs.readString("description"),
                    fs.readInt("quantity"),
                    fs.readDouble("price"))
  }

}

object MiscFields extends Enumeration{

}