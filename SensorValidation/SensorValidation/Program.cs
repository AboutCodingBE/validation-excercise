using Microsoft.EntityFrameworkCore;
using SensorValidation.Persistence;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var sqliteConnection = builder.Configuration.GetConnectionString("SensorValidation");

builder.Services.AddDbContext<SensorValidationDbContext>
            (o => o.UseSqlite(sqliteConnection));

var app = builder.Build();


// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseStaticFiles();
app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
