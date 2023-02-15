using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace SensorValidation.Migrations
{
    public partial class Database_Created : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Sensors",
                columns: table => new
                {
                    Serial = table.Column<long>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    Type = table.Column<string>(type: "TEXT", nullable: false),
                    StatusId = table.Column<int>(type: "INTEGER", nullable: false),
                    CurrentConfiguration = table.Column<string>(type: "TEXT", nullable: false),
                    CurrentFirmware = table.Column<string>(type: "TEXT", nullable: false),
                    CreatedAt = table.Column<DateTime>(type: "TEXT", nullable: false),
                    UpdatedAt = table.Column<DateTime>(type: "TEXT", nullable: false),
                    StatusName = table.Column<string>(type: "TEXT", nullable: false),
                    NextTask = table.Column<long>(type: "INTEGER", nullable: false),
                    TaskCount = table.Column<int>(type: "INTEGER", nullable: false),
                    ActivityStatus = table.Column<string>(type: "TEXT", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Sensors", x => x.Serial);
                });

            migrationBuilder.CreateTable(
                name: "Tasks",
                columns: table => new
                {
                    Id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    SensorSerial = table.Column<long>(type: "INTEGER", nullable: false),
                    Type = table.Column<string>(type: "TEXT", nullable: false),
                    StatusId = table.Column<int>(type: "INTEGER", nullable: false),
                    AttemptCount = table.Column<int>(type: "INTEGER", nullable: false),
                    CreatedAt = table.Column<DateTime>(type: "TEXT", nullable: false),
                    UpdatedAt = table.Column<DateTime>(type: "TEXT", nullable: false),
                    FileId = table.Column<string>(type: "TEXT", nullable: false),
                    StatusName = table.Column<string>(type: "TEXT", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Tasks", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "TaskSensors",
                columns: table => new
                {
                    Id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    SensorSerial = table.Column<long>(type: "INTEGER", nullable: false),
                    FileId = table.Column<string>(type: "TEXT", nullable: false),
                    Type = table.Column<string>(type: "TEXT", nullable: false),
                    SensorModelSerial = table.Column<long>(type: "INTEGER", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TaskSensors", x => x.Id);
                    table.ForeignKey(
                        name: "FK_TaskSensors_Sensors_SensorModelSerial",
                        column: x => x.SensorModelSerial,
                        principalTable: "Sensors",
                        principalColumn: "Serial");
                });

            migrationBuilder.InsertData(
                table: "Sensors",
                columns: new[] { "Serial", "ActivityStatus", "CreatedAt", "CurrentConfiguration", "CurrentFirmware", "NextTask", "StatusId", "StatusName", "TaskCount", "Type", "UpdatedAt" },
                values: new object[] { 1L, "Error", new DateTime(2023, 1, 26, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1191), "a.config", "FW:23-015-2023:03", 1L, 1, "Idle", 3, "TS50X", new DateTime(2023, 1, 26, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1199) });

            migrationBuilder.InsertData(
                table: "Sensors",
                columns: new[] { "Serial", "ActivityStatus", "CreatedAt", "CurrentConfiguration", "CurrentFirmware", "NextTask", "StatusId", "StatusName", "TaskCount", "Type", "UpdatedAt" },
                values: new object[] { 2L, "Error", new DateTime(2023, 1, 26, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1274), "b.config", "FW:23-015-2023:03", 2L, 1, "Idle", 3, "TS50X", new DateTime(2023, 1, 26, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1275) });

            migrationBuilder.InsertData(
                table: "TaskSensors",
                columns: new[] { "Id", "FileId", "SensorModelSerial", "SensorSerial", "Type" },
                values: new object[] { 1, "0e29ede6-e426-4b97-8f3f-342526baa009", null, 2L, "update_configuration" });

            migrationBuilder.InsertData(
                table: "TaskSensors",
                columns: new[] { "Id", "FileId", "SensorModelSerial", "SensorSerial", "Type" },
                values: new object[] { 2, "cdd3e70e-fae0-40d1-ba6c-b26b678beaf5", null, 1L, "update_firmware" });

            migrationBuilder.InsertData(
                table: "Tasks",
                columns: new[] { "Id", "AttemptCount", "CreatedAt", "FileId", "SensorSerial", "StatusId", "StatusName", "Type", "UpdatedAt" },
                values: new object[] { 1, 3, new DateTime(2023, 1, 27, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1298), "", 2L, 0, "Pending", "update_firmware", new DateTime(2023, 1, 27, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1299) });

            migrationBuilder.InsertData(
                table: "Tasks",
                columns: new[] { "Id", "AttemptCount", "CreatedAt", "FileId", "SensorSerial", "StatusId", "StatusName", "Type", "UpdatedAt" },
                values: new object[] { 2, 3, new DateTime(2023, 1, 27, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1387), "", 1L, 0, "Pending", "update_firmware", new DateTime(2023, 1, 27, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1388) });

            migrationBuilder.CreateIndex(
                name: "IX_TaskSensors_SensorModelSerial",
                table: "TaskSensors",
                column: "SensorModelSerial");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Tasks");

            migrationBuilder.DropTable(
                name: "TaskSensors");

            migrationBuilder.DropTable(
                name: "Sensors");
        }
    }
}
