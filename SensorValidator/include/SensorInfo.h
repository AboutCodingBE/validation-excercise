#pragma once
#include "TaskInfo.h"

#include <string>
#include <queue>

namespace SensorValidator
{

struct  Sensor
{
    enum class Status
    {
        Idle,
        Awaiting,
        Updating,
    };

    enum class ActivityStatus
    {
        Online,
        Disconnected,
        Error,
    };

    std::string name;
    std::string type;
    int statusId;
    std::string currentConfiguration;
    std::string currentFirmware;
    std::string createdAt;
    std::string updatedAt;
    Status status;
    Task nextTask;   
    ActivityStatus activityStatus; 
    std::queue<Task> taskQueue;
};

} // namespace SensorValidator