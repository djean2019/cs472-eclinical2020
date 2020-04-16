"use strict";

window.onload = () => {

    let medicineTypes;

    getMedicineTypes();

    function getMedicineTypes() {
        fetch("http://localhost:8080/miuclinical/api/medicinetype")
            .then(response => response.json())
            .then((items) => {
                medicineTypes = items.data;
                medicineTypes.forEach((item, index) => {
                    const row = buildRow(item, index);
                    $(table).find('tbody').append(row);
                })
            })
            .catch(error => alert(error));
    }

    function buildRow(medicineType, index) {
        const row = `
            <tr>
                <input type="hidden" id="index" value="${index}">
                <th scope="row">${index + 1}</th>
                <td>${medicineType.medicineTypeId}</td>
                <td>${medicineType.medicineTypeName}</td>
            </tr>
            `;
        return row;
    }

    $(document).on("click", "table tbody tr", function() {
        document.getElementById("idGroup").style.display = "block";
        document.getElementById("updateButtonContainer").style.display = "block";
        document.getElementById("addButtonContainer").style.display = "none";
        const index = $(this).children("#index").val();
        const item = medicineTypes[index];
        $("#id").val(item.medicineTypeId);
        $("#medicineTypeName").val(item.medicineTypeName);
        $('#myModal').modal("show");
    });

    $("#newMedicineTypeButton").on("click", () => {
        document.getElementById("form").reset();
        document.getElementById("idGroup").style.display = "none";
        document.getElementById("updateButtonContainer").style.display = "none";
        document.getElementById("addButtonContainer").style.display = "block";
        $("#myModal").modal("show");
    });

    $("#addButton").on("click", () => {
        addNewMedicineType();
        $("#myModal").modal("hide");
    });

    $("#updateButton").on("click", () => {
        updateMedicineType();
        $("#myModal").modal("hide");
    });

    $("#deleteButton").on("click", () => {
        deleteMedicineType();
        $("#myModal").modal("hide");
    });

    function addNewMedicineType() {
        const name = $("#medicineTypeName").val();
        const medicine = {
            "medicine_type_name": name,
        }
        const url = "http://localhost:8080/miuclinical/api/medicinetype";
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams(medicine)
        }
        fetch(url, request)
            .then(response => response.json())
            .then(medicineType => {
                console.log(medicineType);
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function deleteMedicineType() {
        const id = document.getElementById("id").value;
        const url = `http://localhost:8080/miuclinical/api/medicinetype?medicine_type_id=${id}`;
        const request = {
            method: "DELETE"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(medicineType => {
                console.log(medicineType);
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function updateMedicineType() {
        const id = document.getElementById("id").value;
        const name = document.getElementById("medicineTypeName").value;
        const url = `http://localhost:8080/miuclinical/api/medicinetype?medicine_type_id=${id}&medicine_type_name=${name}`;
        const request = {
            method: "PUT"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(medicineType => {
                console.log(medicineType);
                location.reload();
            })
            .catch(error => console.log(error));
    }
}