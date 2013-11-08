package org.neesh.asr.mapper

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import org.neesh.asr.beans.{RecordType, PAX}

/**
 * Created by IntelliJ IDEA.
 * User: Shubh Chavda
 * Date: 4/6/11
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */

class PAXFieldSetMapper extends FieldSetMapper[PAX]{


  def mapFieldSet(fs : FieldSet) : PAX = {
    val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))
    return new PAX(recordType, fs.readString("name"))
  }

}

object PAXFields extends Enumeration{

}