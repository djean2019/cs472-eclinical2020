"use strict";

window.onload = () => {
    getDoctors()

    function getDoctors() {
        fetch("../json/doctors.json")
            .then(response => response.json())
            .then((doctors) => {
                doctors.forEach((item, index) => {
                    const fullName = [item.firstName, item.middleName, item.lastName]
                        .filter(item => item !== null)
                        .join(" ")
                    const row = `
                    <tr>
                        <th scope="row">${index + 1}</th>
                        <td>${item.id}</td>
                        <td>${fullName}</td>
                        <td>${item.phoneNumber}</td>
                        <td>${item.address}</td>
                    </tr>
                    `;
                   // $(table).find('tbody').append(row);
                })
            })
            .catch(error => alert(error));
    }

    $("#addButton").on("click", () => {
        addNewAppointment();
        $("#Modal").modal("hide");
    });

  
    function addNewAppointment() {
        //const patientName = document.getElementById("patientName").value;
       // alert(document.getElementById("specialization").value);
        const time = document.getElementById("time").value;
        const appdate = document.getElementById("appdate").value;
        const specialization = document.getElementById("specialization").value;

        const appointment = {
            "speciatation_id": specialization,
            "appointment_time": time,
            "appointment_date": appdate,
            "user_id": getCookie("userId")
        }
        
        const url = "http://localhost:8080/miuclinical/api/appointmentcontroller";
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams(appointment)
        }

        fetch(url, request)
            .then(response => response.json())
            .then(user => {
                console.log(user);
                location.reload();
            })
            .catch(error => console.log(error));
    }

}