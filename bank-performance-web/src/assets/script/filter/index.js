import moment from "moment"
import dateUtil from "../util/date"

export default {
  format(date, formatString = "YYYY-MM-DD") {
    if (date) {
      return moment(date).format(formatString)
    }
    return ""
  },
  toFixed(number, showNaN) {
    let _number = number * 1
    if (typeof _number === "number") {
      return _number.toFixed(2)
    } else if (showNaN) {
      return "NaN"
    } else {
      _number = 0
      return _number.toFixed(2)
    }
  },
  toMore(number, threshold = 100) {
    if (number > threshold) {
      return threshold - 1
    }
    return number
  },
  ago(date) {
    return dateUtil.ago(date)
  },
  getPropByPath(object, prop) {
    let props = prop.split(".")
    let o = object
    for (let p of props) {
      o = o[p]
    }
    return o
  },
  greaterThanEqualToNextDay(p1, p2) {
    if (p1 && p2) {
      let d1 = new Date(p1)
      let d2 = new Date(p2)
      return (
        d2.getFullYear() - d1.getFullYear() > 0 ||
        d2.getMonth() - d1.getMonth() > 0 ||
        d2.getDate() - d1.getDate() > 0
      )
    }
    return false
  }
}
