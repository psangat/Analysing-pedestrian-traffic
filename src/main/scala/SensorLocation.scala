import java.text.SimpleDateFormat
import java.util.Date
class SensorLocation (private var _sensorID: Int = -1,
                      private var _sensorDescription: String = "",
                      private var _sensorName: String = "",
                      private var _installationDate: Date = new Date(),
                      private var _status: String = "",
                      private var _note: String = "",
                      private var _direction1: String = "",
                      private var _direction2: String = "",
                      private var _latitude: Double = 0.0,
                      private var _longitude: Double = 0.0,
                      private var _location: (Double, Double) = (0.0,0.0)) extends Serializable {

  def sensorID = _sensorID
  def sensorID_= (newSensorID: String): Unit = { _sensorID = newSensorID.toInt}

  def sensorDescription = _sensorDescription
  def sensorDescription_= (newSensorDescription: String): Unit = { _sensorDescription = newSensorDescription}

  def sensorName = _sensorName
  def sensorName_= (newSensorName: String): Unit = { _sensorName = newSensorName}

  def installationDate = _installationDate
  def installationDate_= (newInstallationDate: String): Unit = {
    val format = new SimpleDateFormat("yyyy/MM/dd")
    _installationDate = format.parse(newInstallationDate)
  }

  def status = _status
  def status_= (newStatus: String): Unit = { _status = newStatus}

  def note = _note
  def note_= (newNote: String) : Unit = { _note = newNote}

  def direction1 = _direction1
  def direction1_=(newDirection1: String): Unit = { _direction1 = newDirection1}

  def direction2 = _direction2
  def direction2_= (newDirection2: String): Unit = { _direction2 = newDirection2}

  def latitude = _latitude
  def latitude_= (newLatitude: String) : Unit = { _latitude = newLatitude.toDouble}

  def longitude = _longitude
  def longitude_= (newLongitude: String) : Unit = { _longitude = newLongitude.toDouble}

  def location = _location
  def location_= (newLocation: (Double, Double)): Unit = { _location = newLocation}

  override def toString(): String = {
    s"Sensor ID: $sensorID, Sensor Description: $sensorDescription, Sensor Name: $sensorName, Installation Date: $installationDate, Status: $status, Note: $note, Direction 1: $direction1, Direction 2: $direction2, Latitude: $latitude, Longitude: $longitude, Location: $location"
  }
}
