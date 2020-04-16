"use strict";

window.onload = () => {

    let specializations;

    getSpecializations();

    function getSpecializations() {
        fetch("http://localhost:8080/miuclinical/api/specializationcontroller")
            .then(response => response.json())
            .then((items) => {
                specializations = items.data;
                specializations.forEach((item, index) => {
                    const row = buildRow(item, index);
                    $(table).find('tbody').append(row);
                })
            })
            .catch(error => alert(error));
    }

    function buildRow(specialization, index) {
        const row = `
            <tr>
                <input type="hidden" id="index" value="${index}">
                <th scope="row">${index + 1}</th>
                <td>${specialization.specializationId}</td>
                <td>${specialization.specializationName}</td>
            </tr>
            `;
        return row;
    }

    $(document).on("click", "table tbody tr", function() {
        document.getElementById("idGroup").style.display = "block";
        document.getElementById("updateButtonContainer").style.display = "block";
        document.getElementById("addButtonContainer").style.display = "none";
        const index = $(this).children("#index").val();
        const item = specializations[index];
        $("#id").val(item.specializationId);
        $("#specializationName").val(item.specializationName);
        $('#myModal').modal("show");
    });

    $("#newSpecializationButton").on("click", () => {
        document.getElementById("form").reset();
        document.getElementById("idGroup").style.display = "none";
        document.getElementById("updateButtonContainer").style.display = "none";
        document.getElementById("addButtonContainer").style.display = "block";
        $("#myModal").modal("show");
    });

    $("#addButton").on("click", () => {
        addNewSpecialization();
        $("#myModal").modal("hide");
    });

    $("#updateButton").on("click", () => {
        updateSpecialization();
        $("#myModal").modal("hide");
    });

    $("#deleteButton").on("click", () => {
        deleteSpecialization();
        $("#myModal").modal("hide");
    });

    function addNewSpecialization() {
        const name = $("#specializationName").val();
        const medicine = {
            "specialization_name": name,
        }

        const url = "http://localhost:8080/miuclinical/api/specializationcontroller";
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams(medicine)
        }
        fetch(url, request)
            .then(response => response.json())
            .then(specialization => {
                console.log(specialization);
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function deleteSpecialization() {
        const id = document.getElementById("id").value;
        const url = `http://localhost:8080/miuclinical/api/specializationcontroller?specialization_id=${id}`;
        const request = {
            method: "DELETE"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(specialization => {
                console.log(specialization);
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function updateSpecialization() {
        const id = document.getElementById("id").value;
        const name = document.getElementById("specializationName").value;
        const url = `http://localhost:8080/miuclinical/api/specializationcontroller?specialization_id=${id}&specialization_name=${name}`;
        const request = {
            method: "PUT"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(specialization => {
                console.log(specialization);
                location.reload();
            })
            .catch(error => console.log(error));
    }
}