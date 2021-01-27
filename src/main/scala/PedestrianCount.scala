import java.text.SimpleDateFormat
import java.util.Date

class PedestrianCount(private var _id: Int = -1,
                      private var _dateTime: Date =  new Date(),
                      private var _year: Int = -1,
                      private var _month: String = "",
                      private var _mDate: Int = -1,
                      private var _day: String = "",
                      private var _time: Int = -1,
                      private var _sensorID: Int = -1,
                      private var _sensorName: String = "",
                      private var _hourlyCounts: Int = -1) extends Serializable
{
  def id = _id
  def id_=(newID: String): Unit = { _id = newID.toInt}

  def dateTime = _dateTime
  def dateTime_= (newDateTime: String): Unit = {
    val format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
    _dateTime = format.parse(newDateTime)
  }

  def year = _year
  def year_= (newYear: String): Unit = { _year = newYear.toInt}

  def month = _month
  def month_= (newMonth: String): Unit = { _month = newMonth}

  def mDate = _mDate
  def mDate_= (newMDate: String): Unit = {_mDate = newMDate.toInt}

  def day = _day
  def day_= (newDay: String) : Unit = {_day = newDay}

  def time = _time
  def time_= (newTime: String): Unit = { _time = newTime.toInt}

  def sensorID = _sensorID
  def sensorID_= (newSensorID: String): Unit = { _sensorID = newSensorID.toInt}

  def sensorName = _sensorName
  def sensorName_= (newSensorName: String): Unit = { _sensorName = newSensorName}

  def hourlyCounts = _hourlyCounts
  def hourlyCounts_= (newHourlyCounts: String): Unit = { _hourlyCounts = newHourlyCounts.toInt}

  override def toString(): String = {
    s"ID: $id, Date Time: $dateTime, Year: $year, Month: $month, MDate: $mDate, Day: $day, Time: $time, Sensor ID: $sensorID, Sensor Name: $sensorName, Hourly Counts: $hourlyCounts"
  }


}
