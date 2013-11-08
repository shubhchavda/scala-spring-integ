package com.sabre.apd.loyalty.batch.asr.mapper

/**
 * Created by IntelliJ IDEA.
 * User: sg0209008
 * Date: 5/20/11
 * Time: 6:57 PM
 * To change this template use File | Settings | File Templates.
 */

trait FieldSanitizer {

  def sanitize(str : String) : String = {
    str.replace("{", "")
  }


}