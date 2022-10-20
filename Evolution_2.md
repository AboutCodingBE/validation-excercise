# Evolution 2: Do the same for different type of senson

## Outline

The application is proving its worth: we are saving a lot of time and also money. The idea now is to expand the functionality
to another type of sensor: The PTL3000. This sensor also needs to be validated for firmware and configuration compatibility. Its digital inputs
and outputs also need to be validated. This sensor has 1 input and 3 outputs. So the amount of possible combinations that
you need to test is higher. 

The biggest difference with this sensor in regard to the previous sensor is that the firmware version is that the firmware
version is actually a UUID. You need to use this UUID to look up the firmware file and see when the firmware was released. Only 
firmware released on 01-10-2022 is considered to be compatible with the latest configuration. 

**Here is the challenge of the second evolution:** Adjust the application so that it can also validate the PTL3000. In order to 
complete this challenge, you will need to know about the file api. Check the[ Manufactures api](Manufacturer_API.md) for more information.

## Summary of the challenge

* adjust the application to also be able to validate the PTL3000 sensor. 
* For each PTL3000 sensor, validate the firmware and see if the latest configuration is installed. 
* For each PTL3000 sensor, see if the digital inputs and outputs work as expected. 
* The latest update of the status is returned by the application. 