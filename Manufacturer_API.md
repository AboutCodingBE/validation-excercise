# Manufacturer API


## Sensors

#### GET # www.mysensor.io/api/sensors/{id}

API endpoint to get information about a single sensor

```json
{
  "serial": 123456789098789,
  "status_id": 1,
  "current_configuration": "G1_350424060716329_2022-09-08T10:02:15.477317252.cfg",
  "current_firmware": "FW:23-09-2022:03",
  "created_at": "2022-03-31 11:26:08",
  "updated_at": "2022-10-18 17:53:48",
  "status_name": "Idle",
  "next_task": null,
  "task_count": 5,
  "activity_status": "Online",
  "task_queue": "Empty"
}
```

## Tasks

#### GET # www.mysensor.io/api/tasks/{id}

API endpoint to get information about a single task

```json
{
  "id": 16624171,
  "sensor_serial": 12345678909789,
  "type": "update_firmware",
  "status_id": 2,
  "attempt_count": 3,
  "created_at": "2022-10-17 16:00:07",
  "updated_at": "2022-10-17 16:49:39",
  "file_id": 1920504,
  "status_name": "Completed",
}
```

#### GET # www.mysensor.io/api/tasks/{id}

