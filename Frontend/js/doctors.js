"use strict";

window.onload = () => {

    let specializations;
    let doctors;

    getSpecializations();
    getDoctors();

    function getDoctors() {
        fetch("http://localhost:8080/miuclinical/api/doctorcontroller")
            .then(response => response.json())
            .then(items => {
                doctors = items.data;
                doctors.forEach((item, index) => {
                    const row = buildRow(item, index);
                    $(table).find('tbody').append(row);
                })
            })
            .catch(error => alert(error));
    }

    function getSpecializations() {
        fetch("http://localhost:8080/miuclinical/api/specializationcontroller")
            .then(response => response.json())
            .then((items) => {
                specializations = items.data;
            })
            .catch(error => alert(error));
    }

    function buildRow(doctor, index) {
        const fullName = [doctor.firstName, doctor.middleName, doctor.lastName]
            .filter(item => item !== null)
            .join(" ")
        const row = `
            <tr>
                <input type="hidden" id="index" value="${index}">
                <th scope="row">${index + 1}</th>
                <td>${doctor.personId}</td>
                <td>${fullName}</td>
                <td>${doctor.specialization.map(item => item.specializationName).join(", ")}</td>
                <td>${doctor.contactPhone}</td>
                <td>${doctor.address}</td>
            </tr>
            `;
        return row;
    }

    $(document).on("click", "table tbody tr", function() {
        document.getElementById("idGroup").style.display = "block";
        document.getElementById("updateButtonContainer").style.display = "block";
        document.getElementById("addButtonContainer").style.display = "none";
        const index = $(this).children("#index").val();
        const item = doctors[index];
        document.getElementById("id").value = item.personId;
        document.getElementById("firstName").value = item.firstName;
        document.getElementById("middleName").value = item.middleName;
        document.getElementById("lastName").value = item.lastName;
        document.getElementById("phoneNumber").value = item.contactPhone;
        document.getElementById("address").value = item.address;
        $("#specialization").empty();
        const specializationOptions = $("#specialization");
        $.each(specializations, function(index, item) {
            specializationOptions.append(new Option(item.specializationName, item.specializationId));
        });
        $("#specialization").val(item.specialization[0].specializationId);
        $('#updateModal').modal("show");
    });

    $("#newDoctorButton").on("click", () => {
        document.getElementById("form").reset();
        document.getElementById("idGroup").style.display = "none";
        document.getElementById("updateButtonContainer").style.display = "none";
        document.getElementById("addButtonContainer").style.display = "block";
        $("#specialization").empty();
        const specializationOptions = $("#specialization");
        $.each(specializations, function(index, item) {
            specializationOptions.append(new Option(item.specializationName, item.specializationId));
        });
        $("#updateModal").modal("show");
    });

    $("#addButton").on("click", () => {
        addNewDoctor();
        $("#updateModal").modal("hide");
    });

    $("#updateButton").on("click", () => {
        updateDoctor();
        $("#updateModal").modal("hide");
    });

    $("#deleteButton").on("click", () => {
        deleteDoctor();
        $("#updateModal").modal("hide");
    });

    function addNewDoctor() {
        const firstName = document.getElementById("firstName").value;
        const middleName = document.getElementById("middleName").value;
        const lastName = document.getElementById("lastName").value;
        const phoneNumber = document.getElementById("phoneNumber").value;
        const address = document.getElementById("address").value;
        const selectedSpecialization = $("#specialization").val();

        const doctor = {
            "first_name": firstName,
            "middle_name": middleName,
            "last_name": lastName,
            "contact_phone": phoneNumber,
            "address": address,
            "specializations": selectedSpecialization
        }
        console.log(doctor);
        const url = "http://localhost:8080/miuclinical/api/doctorcontroller";
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams(doctor)
        }

        fetch(url, request)
            .then(response => response.json())
            .then(doctor => {
                console.log(doctor);
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function updateDoctor() {
        const id = document.getElementById("id").value;
        const firstName = document.getElementById("firstName").value;
        const middleName = document.getElementById("middleName").value;
        const lastName = document.getElementById("lastName").value;
        const phoneNumber = document.getElementById("phoneNumber").value;
        const address = document.getElementById("address").value;
        const selectedSpecialization = $("#specialization").val();
        const url = `http://localhost:8080/miuclinical/api/doctorcontroller?doctor_id=${id}&first_name=${firstName}&middle_name=${middleName}&last_name=${lastName}&contact_phone=${phoneNumber}&address=${address}&specializations=${selectedSpecialization}`;
        const request = {
            method: "PUT"
        }

        console.log(url);

        fetch(url, request)
            .then(response => response.json())
            .then(user => {
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function deleteDoctor() {
        const id = document.getElementById("id").value;
        const url = `http://localhost:8080/miuclinical/api/doctorcontroller?doctor_id=${id}`;
        const request = {
            method: "DELETE"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(doctor => {
                console.log(doctor);
                location.reload();
            })
            .catch(error => console.log(error));
    }
}