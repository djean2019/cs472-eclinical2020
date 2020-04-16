"use strict";

window.onload = () => {

    let diseases;
    let diseaseTypes;

    getDiseaseTypes();

    function getDiseaseTypes() {
        fetch("http://localhost:8080/miuclinical/api/deseasecategory")
            .then(response => response.json())
            .then((items) => {
                diseaseTypes = items.data;
            })
            .catch(error => alert(error));
    }

    getDiseases();

    function getDiseases() {
        fetch("http://localhost:8080/miuclinical/api/desease")
            .then(response => response.json())
            .then(items => {
                diseases = items.data;
                diseases.forEach((item, index) => {
                    const row = buildRow(item, index);
                    $(table).find('tbody').append(row);
                })
            })
            .catch(error => alert(error));
    }

    function buildRow(disease, index) {
        const row = `
            <tr>
                <input type="hidden" id="index" value="${index}">
                <th scope="row">${index + 1}</th>
                <td>${disease.deseaseId}</td>
                <td>${disease.deseaseName}</td>
                <td>${disease.deseaseCategory.deseaseTypeName}</td>
            </tr>
            `;
        return row;
    }

    $(document).on("click", "table tbody tr", function() {
        document.getElementById("idGroup").style.display = "block";
        document.getElementById("updateButtonContainer").style.display = "block";
        document.getElementById("addButtonContainer").style.display = "none";
        const index = $(this).children("#index").val();
        const item = diseases[index];
        $("#id").val(item.deseaseId);
        $("#diseaseName").val(item.deseaseName);
        $("#diseaseType").empty();
        const types = $("#diseaseType");
        $.each(diseaseTypes, function(index, item) {
            types.append(new Option(item.deseaseTypeName, item.deseaseTypeId));
        });
        $("#diseaseType").val(item.deseaseCategory.deseaseTypeId);
        $('#myModal').modal("show");
    });

    $("#newDiseaseButton").on("click", () => {
        document.getElementById("form").reset();
        document.getElementById("idGroup").style.display = "none";
        document.getElementById("updateButtonContainer").style.display = "none";
        document.getElementById("addButtonContainer").style.display = "block";
        $("#diseaseType").empty();
        const types = $("#diseaseType");
        $.each(diseaseTypes, function(index, item) {
            types.append(new Option(item.deseaseTypeName, item.deseaseTypeId));
        });
        $("#myModal").modal("show");
    });

    $("#addButton").on("click", () => {
        addNewDisease();
        $("#myModal").modal("hide");
    });

    $("#updateButton").on("click", () => {
        updateDisease();
        $("#myModal").modal("hide");
    });

    $("#deleteButton").on("click", () => {
        deleteDisease();
        $("#myModal").modal("hide");
    });

    function addNewDisease() {
        const name = $("#diseaseName").val();
        const type = $("#diseaseType").val();
        const disease = {
            "desease_name": name,
            "desease_type_id": type
        }

        console.log(disease);

        const url = "http://localhost:8080/miuclinical/api/desease";
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams(disease)
        }
        fetch(url, request)
            .then(response => response.json())
            .then(disease => {
                console.log(disease);
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function updateDisease() {
        const id = document.getElementById("id").value;
        const name = document.getElementById("diseaseName").value;
        const typeId = $("#diseaseType").val();
        const url = `http://localhost:8080/miuclinical/api/desease?desease_id=${id}&desease_name=${name}&desease_type_id=${typeId}`;
        const request = {
            method: "PUT"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(disease => {
                console.log(disease);
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function deleteDisease() {
        const id = document.getElementById("id").value;
        const url = `http://localhost:8080/miuclinical/api/desease?desease_id=${id}`;
        const request = {
            method: "DELETE"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(disease => {
                console.log(disease);
                location.reload();
            })
            .catch(error => console.log(error));
    }
}