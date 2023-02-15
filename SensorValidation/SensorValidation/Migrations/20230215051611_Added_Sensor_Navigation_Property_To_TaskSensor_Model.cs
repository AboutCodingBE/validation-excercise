using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace SensorValidation.Migrations
{
    public partial class Added_Sensor_Navigation_Property_To_TaskSensor_Model : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_TaskSensors_Sensors_SensorModelSerial",
                table: "TaskSensors");

            migrationBuilder.DropIndex(
                name: "IX_TaskSensors_SensorModelSerial",
                table: "TaskSensors");

            migrationBuilder.DropColumn(
                name: "SensorModelSerial",
                table: "TaskSensors");

            migrationBuilder.UpdateData(
                table: "Sensors",
                keyColumn: "Serial",
                keyValue: 1L,
                columns: new[] { "CreatedAt", "UpdatedAt" },
                values: new object[] { new DateTime(2023, 1, 26, 5, 16, 10, 875, DateTimeKind.Utc).AddTicks(1704), new DateTime(2023, 1, 26, 5, 16, 10, 875, DateTimeKind.Utc).AddTicks(1710) });

            migrationBuilder.UpdateData(
                table: "Sensors",
                keyColumn: "Serial",
                keyValue: 2L,
                columns: new[] { "CreatedAt", "UpdatedAt" },
                values: new object[] { new DateTime(2023, 1, 26, 5, 16, 10, 875, DateTimeKind.Utc).AddTicks(1768), new DateTime(2023, 1, 26, 5, 16, 10, 875, DateTimeKind.Utc).AddTicks(1769) });

            migrationBuilder.UpdateData(
                table: "TaskSensors",
                keyColumn: "Id",
                keyValue: 1,
                column: "FileId",
                value: "51372ed9-d15a-418d-aeb0-88044bca3327");

            migrationBuilder.UpdateData(
                table: "TaskSensors",
                keyColumn: "Id",
                keyValue: 2,
                column: "FileId",
                value: "102df6d0-f17d-4283-b28a-537c30dfc540");

            migrationBuilder.UpdateData(
                table: "Tasks",
                keyColumn: "Id",
                keyValue: 1,
                columns: new[] { "CreatedAt", "UpdatedAt" },
                values: new object[] { new DateTime(2023, 1, 27, 5, 16, 10, 875, DateTimeKind.Utc).AddTicks(1786), new DateTime(2023, 1, 27, 5, 16, 10, 875, DateTimeKind.Utc).AddTicks(1787) });

            migrationBuilder.UpdateData(
                table: "Tasks",
                keyColumn: "Id",
                keyValue: 2,
                columns: new[] { "CreatedAt", "UpdatedAt" },
                values: new object[] { new DateTime(2023, 1, 27, 5, 16, 10, 875, DateTimeKind.Utc).AddTicks(1863), new DateTime(2023, 1, 27, 5, 16, 10, 875, DateTimeKind.Utc).AddTicks(1864) });

            migrationBuilder.CreateIndex(
                name: "IX_TaskSensors_SensorSerial",
                table: "TaskSensors",
                column: "SensorSerial");

            migrationBuilder.AddForeignKey(
                name: "FK_TaskSensors_Sensors_SensorSerial",
                table: "TaskSensors",
                column: "SensorSerial",
                principalTable: "Sensors",
                principalColumn: "Serial",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_TaskSensors_Sensors_SensorSerial",
                table: "TaskSensors");

            migrationBuilder.DropIndex(
                name: "IX_TaskSensors_SensorSerial",
                table: "TaskSensors");

            migrationBuilder.AddColumn<long>(
                name: "SensorModelSerial",
                table: "TaskSensors",
                type: "INTEGER",
                nullable: true);

            migrationBuilder.UpdateData(
                table: "Sensors",
                keyColumn: "Serial",
                keyValue: 1L,
                columns: new[] { "CreatedAt", "UpdatedAt" },
                values: new object[] { new DateTime(2023, 1, 26, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1191), new DateTime(2023, 1, 26, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1199) });

            migrationBuilder.UpdateData(
                table: "Sensors",
                keyColumn: "Serial",
                keyValue: 2L,
                columns: new[] { "CreatedAt", "UpdatedAt" },
                values: new object[] { new DateTime(2023, 1, 26, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1274), new DateTime(2023, 1, 26, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1275) });

            migrationBuilder.UpdateData(
                table: "TaskSensors",
                keyColumn: "Id",
                keyValue: 1,
                column: "FileId",
                value: "0e29ede6-e426-4b97-8f3f-342526baa009");

            migrationBuilder.UpdateData(
                table: "TaskSensors",
                keyColumn: "Id",
                keyValue: 2,
                column: "FileId",
                value: "cdd3e70e-fae0-40d1-ba6c-b26b678beaf5");

            migrationBuilder.UpdateData(
                table: "Tasks",
                keyColumn: "Id",
                keyValue: 1,
                columns: new[] { "CreatedAt", "UpdatedAt" },
                values: new object[] { new DateTime(2023, 1, 27, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1298), new DateTime(2023, 1, 27, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1299) });

            migrationBuilder.UpdateData(
                table: "Tasks",
                keyColumn: "Id",
                keyValue: 2,
                columns: new[] { "CreatedAt", "UpdatedAt" },
                values: new object[] { new DateTime(2023, 1, 27, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1387), new DateTime(2023, 1, 27, 5, 4, 1, 122, DateTimeKind.Utc).AddTicks(1388) });

            migrationBuilder.CreateIndex(
                name: "IX_TaskSensors_SensorModelSerial",
                table: "TaskSensors",
                column: "SensorModelSerial");

            migrationBuilder.AddForeignKey(
                name: "FK_TaskSensors_Sensors_SensorModelSerial",
                table: "TaskSensors",
                column: "SensorModelSerial",
                principalTable: "Sensors",
                principalColumn: "Serial");
        }
    }
}
