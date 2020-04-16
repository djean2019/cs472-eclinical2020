"use strict";

window.onload = () => {
    
   
    let appointments;
    
     getAppointments();

     getMedicine();

     getdisease();

            
        function getAppointments() {
            fetch("http://localhost:8080/miuclinical/api/todayappointmentcontroller")
            .then(response => response.json())
            .then((items) => {
                
                appointments = items.data;
                console.log(appointments)
                appointments.forEach((item, index) => {
                    const row = buildRow(item, index);
                    
                    $(table).find('tbody').append(row);
                })
            })
            .catch(error => alert(error));
            
        }
    
        function buildRow(appointment, index) {
                console.log(appointment.appointmentDate);
            const row = `
                        <tr>
                            <input type="hidden" id="index" value="${index}">
                            <th scope="row">${index + 1}</th>
                            <td>${appointment.appointmentDate.year}-${appointment.appointmentDate.month}-${appointment.appointmentDate.day}</td>
                            <td>${appointment.appointmentTime.hour}:${appointment.appointmentTime.minute}</td >
                            <td>${appointment.specialization.specializationName}</td>
                            <td>${appointment.appointedBy.username}</td>
                        </tr>
                        `;
            return row;
        }

        function getMedicine() {
            fetch("http://localhost:8080/miuclinical/api/medicine")
            .then(response => response.json())
            .then((items) => {
                
               let medicines = items.data;
                console.log(medicines)
                medicines.forEach((item, index) => {
                   const row = buildCheckbox(item, index);
                    
                   $("#medicineCheckbox").append(row);
                })
            })
            .catch(error => alert(error));
            
        }
    
        function buildCheckbox(medicine, index) {
                //console.log(medicine.medicineTypeName);
            const row = `
                             <input class="form-check-input" type="checkbox" value="${medicine.medicineId}">
                             <label class="form-check-label" for="defaultCheck1">${medicine.medicineName}</label>
                            <br>

                       
                        `;
            return row;
        }

        function getdisease() {
            fetch("http://localhost:8080/miuclinical/api/desease")
            .then(response => response.json())
            .then((items) => {
                
               let diseases = items.data;
                console.log(diseases)
                diseases.forEach((item, index) => {
                   const row = buildCheckbox2(item, index);
                    
                   $("#diseaseCheckbox").append(row);
                })
            })
            .catch(error => alert(error));
            
        }
    
        function buildCheckbox2(disease, index) {
               // console.log(medicine.medicineTypeName);
            const row = `
                             <input class="form-check-input" type="checkbox" value="${disease.deseaseId}" >
                             <label class="form-check-label" for="defaultCheck1">${disease.deseaseName}</label>
                            <br>

                       
                        `;
            return row;
        }

        function formatMonth(month) {
            if(month < 10) 
                return "0" + month;
            return month;
        }

        function formatDate(date) {
            let month = parseInt(date.getUTCMonth()) + 1;
            return date.getFullYear() + "-" + formatMonth(month) + "-" + formatMonth(date.getUTCDate()) + "T" + formatMonth(date.getHours()) + ":" + formatMonth(date.getMinutes()) + ":" + formatMonth(date.getSeconds());
        }

        let indexVal;
        $(document).on("click", "table tbody tr", function() {
            const index = $(this).children("#index").val();
            const item = appointments[index];
            indexVal = index;
            console.log(item.appointedBy.username);
            document.getElementById("patientName").innerText = item.appointedBy.person.firstName + " " + item.appointedBy.person.middleName + " " + item.appointedBy.person.lastName;
            //document.getElementById("doctorName").innerText = item.appointedBy.username;
            
            document.getElementById("checkInDateTime").innerText = formatDate(new Date())
            
            // document.getElementById("userType").selectedIndex = parseInt(item.userType) - 1;
            $('#myModal').modal("show");
        });
    
    
    
        $("#checkInOutBtn").on("click", () => {
            checkInOut();
            //$("#myModal").modal("hide");
        });

    
           
        function checkInOut() {
            const item = appointments[indexVal];
            console.log(item)
            const descritption = document.getElementById("descritption").value;

            const charge = document.getElementById("charge").value;
            const doctor = 1;
            const checkInDateTime =  document.getElementById("checkInDateTime").innerText;
            const checkOutDateTime = formatDate(new Date())

        
            const checkInOut = {
                "appointment_id": item.appointmentId,
                "doctor_id": doctor,
                "checkin_datetime": checkInDateTime,
                "checkout_datetime": checkOutDateTime,
                "charge": charge,
                "description": descritption
            }
            console.log(checkInOut);
            const url = "http://localhost:8080/miuclinical/api/checkinoutcontroller";
            const request = {
               
                method: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams(checkInOut)
            }
            fetch(url, request)
                .then(response => response.json())
                .then(checkInOut => console.log(checkInOut))
                .catch(error => console.log(error));
        }

}