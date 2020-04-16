"use strict";

window.onload = () => {

    let diseaseTypes;

    getDiseaseTypes();

    function getDiseaseTypes() {
        fetch("http://localhost:8080/miuclinical/api/deseasecategory")
            .then(response => response.json())
            .then((items) => {
                diseaseTypes = items.data;
                console.log(diseaseTypes);
                diseaseTypes.forEach((item, index) => {
                    const row = buildRow(item, index);
                    $(table).find('tbody').append(row);
                })
            })
            .catch(error => alert(error));
    }

    function buildRow(diseaseType, index) {
        const row = `
            <tr>
                <input type="hidden" id="index" value="${index}">
                <th scope="row">${index + 1}</th>
                <td>${diseaseType.deseaseTypeId}</td>
                <td>${diseaseType.deseaseTypeName}</td>
            </tr>
            `;
        return row;
    }

    $(document).on("click", "table tbody tr", function() {
        document.getElementById("idGroup").style.display = "block";
        document.getElementById("updateButtonContainer").style.display = "block";
        document.getElementById("addButtonContainer").style.display = "none";
        const index = $(this).children("#index").val();
        const item = diseaseTypes[index];
        $("#id").val(item.deseaseTypeId);
        $("#diseaseTypeName").val(item.deseaseTypeName);
        $('#myModal').modal("show");
    });

    $("#newDiseaseTypeButton").on("click", () => {
        document.getElementById("form").reset();
        document.getElementById("idGroup").style.display = "none";
        document.getElementById("updateButtonContainer").style.display = "none";
        document.getElementById("addButtonContainer").style.display = "block";
        $("#myModal").modal("show");
    });

    $("#addButton").on("click", () => {
        addNewDiseaseType();
        $("#myModal").modal("hide");
    });

    $("#updateButton").on("click", () => {
        updateDiseaseType();
        $("#myModal").modal("hide");
    });

    $("#deleteButton").on("click", () => {
        deleteDiseaseType();
        $("#myModal").modal("hide");
    });

    function addNewDiseaseType() {
        const name = $("#diseaseTypeName").val();
        const disease = {
            "desease_type_name": name,
        }

        console.log(disease);
        const url = "http://localhost:8080/miuclinical/api/deseasecategory";
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams(disease)
        }
        fetch(url, request)
            .then(response => response.json())
            .then(diseaseType => {
                console.log(diseaseType);
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function deleteDiseaseType() {
        const id = document.getElementById("id").value;
        const url = `http://localhost:8080/miuclinical/api/deseasecategory?desease_type_id=${id}`;
        const request = {
            method: "DELETE"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(diseaseType => {
                console.log(diseaseType);
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function updateDiseaseType() {
        const id = document.getElementById("id").value;
        const name = document.getElementById("diseaseTypeName").value;
        const url = `http://localhost:8080/miuclinical/api/deseasecategory?desease_type_id=${id}&desease_type_name=${name}`;
        const request = {
            method: "PUT"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(diseaseType => {
                console.log(diseaseType);
                location.reload();
            })
            .catch(error => console.log(error));
    }
}