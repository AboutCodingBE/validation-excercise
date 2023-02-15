from sensors import sensors;

"""
1. how might the current firmware be needed?
2. Rewrite functions to work as an endpoint not pure functions
"""

# write a function
# this function validates the sensor of an incoming shipment
# the function takes an argument - the argument is a list of sensors id
# and it will show which sensors are okay and which sensors are not

# the function also states clearly what is not okay about a sensor
# for instance, the firmware of the sensor might not be okay: the application would say so

# *** if the sensor has incompatible firmware with the latest configuration - it should indicate this until
# the firmware and configuration have been installed

# The firmware is compatible starting from version FW:23-09-2022:03.
# if the firmware is not compatible, schedule a task to install this configuration

def sensorValidator(id):
  # sensor API: www.mysensor.io/api/sensors/{id}
  # if id corresponds with a list of ids,
  # return the status of the sensor
  for sensor in sensors:
    if sensor["serial"] == id:
      firmwareDate = sensor["current_firmware"].split(":")[1].split("-")
      if( (int(firmwareDate[0])) < 23 or int(firmwareDate[1]) < 9 and int(firmwareDate[2]) < 2022):
        updateFirmware(sensor)
      else:
        return f'Sensor status is okay and up-to-date'
        
    
    
  
  
def updateFirmware(sensor):
  print(sensor)
  
# def errorValidating(id):
#   if len(str(id)) < 15:
#     return "Invalid Sensor Id"
#   else:
#     return "Sensor Id is not recognized"
  
isSensorValid = sensorValidator(123456789067890)
print(isSensorValid)