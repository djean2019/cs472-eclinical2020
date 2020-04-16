"use strict";

window.onload = () => {
    
        //

        let patients;

        getPatients()

        $("#addPatient").on("click", () => {
            addNewPatient();
        });
    
        $("#updatePatient").on("click", () => {
            updatePatient();
        });
        
        $("#removePatient").on("click", () => {
            deletePatient();
        });

    function getPatients() {
        fetch("http://localhost:8080/miuclinical/api/patientcontroller")
            .then(response => response.json())
            .then((items) => {
                patients = items.data;
                items.data.forEach((item, index) => {
                    const row = buildRow(item, index);
                    //
                    $(table).find('tbody').append(row);
                })
            })
            .catch(error => alert(error));
    }
    function buildRow(patient, index){
        const fullName = [patient.firstName, patient.middleName, patient.lastName]
                        .filter(item => item !== null)
                        .join(" ")
                    const row = `
                    <tr>
                        <input type="hidden" id="index" value="${index}">
                        <th scope="row">${index + 1}</th>
                        <td>${patient.personId}</td>
                        <td>${fullName}</td>
                        <td>${patient.contactPhone}</td>
                        <td>${patient.address}</td>
                    </tr>
                    `;
            return row;
    }

    $(document).on("click", "table tbody tr", function() {
        const index = $(this).children("#index").val();
        const item = patients[index];
        console.log(patients[index].personId);
                //document.getElementById("patientNumber").value = item.id;
        document.getElementById("patientId").value = item.personId;
        document.getElementById("firstName").value = item.firstName;
        document.getElementById("middleName").value = item.middleName;
        document.getElementById("lastName").value = item.lastName;
        document.getElementById("phoneNumber").value = item.contactPhone;
        document.getElementById("address").value = item.address;
        
        
    });
   
    function addNewPatient() {
        
        const firstName = document.getElementById("firstName").value;
        const middleName = document.getElementById("middleName").value;
        const lastName = document.getElementById("lastName").value;
        const phoneNumber = document.getElementById("phoneNumber").value;
        const address = document.getElementById("address").value;
       // console.log(firstName);
        const patient = {
            "first_name": firstName,
            "middle_name": middleName,
            "last_name": lastName,
            "contact_phone": phoneNumber,
            "address": address
        }
       // console.log(patient);
        const url = "http://localhost:8080/miuclinical/api/patientcontroller";
        const request = {
           
            method: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(patient)
        }
        fetch(url, request)
            .then(response => response.json())
            .then(patient => console.log(patient))
            .catch(error => console.log(error));
    }

    function updatePatient() {
        const patientId = document.getElementById("patientId").value;
        const firstName = document.getElementById("firstName").value;
        const middleName = document.getElementById("middleName").value;
        const lastName = document.getElementById("lastName").value;
        const phoneNumber = document.getElementById("phoneNumber").value;
        const address = document.getElementById("address").value;
       
        // const index = $(this).children("#index").val();
        // console.log(patients[index].personId);
        
        const patient = {
            "patient_id": patientId,
            "first_name": firstName,
            "middle_name": middleName,
            "last_name": lastName,
            "contact_phone": phoneNumber,
            "address": address
        }
        //console.log(patient);
        const url = "http://localhost:8080/miuclinical/api/patientcontroller?patient_id="+ patientId + "&first_name=" + firstName + "&middle_name=" + middleName + "&last_name=" + lastName + "&contact_phone=" + phoneNumber + "&address=" + address;
        const request = {
           
            method: "put",
            
        }
        fetch(url, request)
            .then(response => response.json())
            .then(patient => console.log(patient))
            .catch(error => console.log(error));
    }

    function deletePatient() {
        const patientId = document.getElementById("patientId").value;     
        
        const patient = {
            "patient_id": patientId
        }
    
        const url = "http://localhost:8080/miuclinical/api/patientcontroller?patient_id=" + patientId;
        const request = {
           
            method: "delete"
            
        }
        fetch(url, request)
            .then(response => response.json())
            .then(patient => console.log(patient))
            .catch(error => console.log(error));
    }


}