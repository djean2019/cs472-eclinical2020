"use strict";

window.onload = () => {

    let patients;

    getPatients();

    function getPatients() {
        fetch("http://localhost:8080/miuclinical/api/patientcontroller")
            .then(response => response.json())
            .then((items) => {
                patients = items.data.map(person => {
                    return {
                        "id": person.personId,
                        "firstName": person.firstName,
                        "middleName": person.middleName,
                        "lastName": person.lastName,
                        "phoneNumber": person.contactPhone,
                        "address": person.address
                    }
                });
                patients.forEach((item, index) => {
                    const row = buildRow(item, index);
                    $(table).find('tbody').append(row);
                })
            })
            .catch(error => alert(error));
    }

    function buildRow(patients, index) {
        const fullName = [patients.firstName, patients.middleName, patients.lastName]
            .filter(item => item !== null)
            .join(" ")
        const row = `
            <tr>
                <input type="hidden" id="index" value="${index}">
                <th scope="row">${index + 1}</th>
                <td class="id">${patients.id}</td>
                <td class="fname">${fullName}</td>
                <td class = "phone">${patients.phoneNumber}</td>
                <td class = "address">${patients.address}</td>
            </tr>
            `;
        return row;
    }

    $(document).on("click", ".btnEdit", function() {
        var $row = $(this).closest("tr"); // Find the row
        var $id = $row.find(".id").text(); // Find the text id
        var $fname = $row.find(".fname").text(); // Find the text name
        var $phone = $row.find(".phone").text(); // Find the text phone
        var $address = $row.find(".address").text(); // Find the text address
        $("#patientId").val($id);
        $("#patientName").val($fname);
        $("#patientPhone").val($phone);
        $("#patientAddress").val($address);
    });
    $(document).on("click", ".btnDelete", function() {
        var $row = $(this).closest("tr"); // Find the row
        var $id = $row.find(".id").text(); // Find the text id
        $("#deletePatientId").val($id);
    });
}