#pragma once 

#include <string>
#include <cstdint>

namespace SensorValidator
{

struct Task
{
    enum class Type
    {
        UpdateFirmware,
        UpdateConfiguration,
    };

    enum class Status
    {
        Pending,
        Completed,
        Error,
        Ongoing,
    };

    uint16_t id;
    uint64_t sensorSerial;
    Type type;
    int statusId;
    int attemptCount;
    std::string createdAt;
    std::string updatedAt;
    std::string fileId;
    Status taskStatus;
};

} // namespace SensorValidator