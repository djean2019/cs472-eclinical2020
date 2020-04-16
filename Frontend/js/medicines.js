"use strict";

window.onload = () => {

    let medicines;

    let medicineTypes;

    getMedicineTypes();

    function getMedicineTypes() {
        fetch("http://localhost:8080/miuclinical/api/medicinetype")
            .then(response => response.json())
            .then((items) => {
                medicineTypes = items.data;
            })
            .catch(error => alert(error));
    }

    getMedicines();

    function getMedicines() {
        fetch("http://localhost:8080/miuclinical/api/medicine")
            .then(response => response.json())
            .then(items => {
                medicines = items.data;
                medicines.forEach((item, index) => {
                    const row = buildRow(item, index);
                    $(table).find('tbody').append(row);
                })
            })
            .catch(error => alert(error));
    }

    function buildRow(medicine, index) {
        const row = `
            <tr>
                <input type="hidden" id="index" value="${index}">
                <th scope="row">${index + 1}</th>
                <td>${medicine.medicineId}</td>
                <td>${medicine.medicineName}</td>
                <td>${medicine.medicineType.medicineTypeName}</td>
            </tr>
            `;
        return row;
    }

    $(document).on("click", "table tbody tr", function() {
        document.getElementById("idGroup").style.display = "block";
        document.getElementById("updateButtonContainer").style.display = "block";
        document.getElementById("addButtonContainer").style.display = "none";
        const index = $(this).children("#index").val();
        const item = medicines[index];
        $("#id").val(item.medicineId);
        $("#medicineName").val(item.medicineName);
        $("#medicineType").empty();
        const types = $("#medicineType");
        $.each(medicineTypes, function(index, item) {
            types.append(new Option(item.medicineTypeName, item.medicineTypeId));
        });
        $("#medicineType").val(item.medicineType.medicineTypeId);
        $('#myModal').modal("show");
    });

    $("#newMedicineButton").on("click", () => {
        document.getElementById("form").reset();
        document.getElementById("idGroup").style.display = "none";
        document.getElementById("updateButtonContainer").style.display = "none";
        document.getElementById("addButtonContainer").style.display = "block";
        $("#medicineType").empty();
        const types = $("#medicineType");
        console.log(medicineTypes);
        $.each(medicineTypes, function(index, item) {
            types.append(new Option(item.medicineTypeName, item.medicineTypeId));
        });
        $("#myModal").modal("show");
    });

    $("#addButton").on("click", () => {
        addNewMedicine();
        $("#myModal").modal("hide");
    });

    $("#updateButton").on("click", () => {
        updateMedicine();
        $("#myModal").modal("hide");
    });

    $("#deleteButton").on("click", () => {
        deleteMedicine();
        $("#myModal").modal("hide");
    });

    function addNewMedicine() {
        const name = $("#medicineName").val();
        const type = $("#medicineType").val();
        const medicine = {
            "medicine_name": name,
            "medicine_type_id": type
        }

        console.log(medicine);

        const url = "http://localhost:8080/miuclinical/api/medicine";
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams(medicine)
        }
        fetch(url, request)
            .then(response => response.json())
            .then(medicine => {
                console.log(medicine);
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function updateMedicine() {
        const id = document.getElementById("id").value;
        const name = document.getElementById("medicineName").value;
        const typeId = $("#medicineType").val();
        const url = `http://localhost:8080/miuclinical/api/medicine?medicine_id=${id}&medicine_name=${name}&medicine_type_id=${typeId}`;
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

    function deleteMedicine() {
        const id = document.getElementById("id").value;
        const url = `http://localhost:8080/miuclinical/api/medicine?medicine_id=${id}`;
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
}