"use strict";

window.onload = () => {

    let users;
    let patients;
    let doctors;

    getUsers();
    getPatients();
    getDoctors();

    function getUsers() {
        const url = "http://localhost:8080/miuclinical/api/usercontroller";
        const request = {
            method: "GET",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        }

        fetch(url, request)
            .then(response => response.json())
            .then(item => {
                users = item.data;
                users.forEach((item, index) => {
                    const row = buildRow(item, index);
                    $(table).find('tbody').append(row);
                })

            })
            .catch(error => console.log(error));
    }

    function buildRow(user, index) {
        let userType = "Admin";
        if (user.userType.userTypeId === 2) {
            userType = "Patient";
        } else if (user.userType.userTypeId === 3) {
            userType = "Doctor";
        }

        const row = `
                    <tr>
                        <input type="hidden" id="index" value="${index}">
                        <th scope="row">${index + 1}</th>
                        <td>${user.userId}</td>
                        <td>${user.username}</td>
                        <td>${userType}</td>
                        <td>${user.isLock}</td>
                    </tr>
                    `;
        return row;
    }

    $(document).on("click", "table tbody tr", function() {
        document.getElementById("idGroup").style.display = "block";
        document.getElementById("updateButtonContainer").style.display = "block";
        document.getElementById("addButtonContainer").style.display = "none";
        document.getElementById("isLockedContainer").style.display = "block";
        const index = $(this).children("#index").val();
        const item = users[index];
        document.getElementById("id").value = item.userId;
        document.getElementById("userName").value = item.username;
        document.getElementById("isLocked").checked = item.isLock;
        document.getElementById("userType").selectedIndex = parseInt(item.userType.userTypeId) - 1;
        if (item.userType.userTypeId === 1) {
            document.getElementById("accountForContainer").style.display = "none";
        } else {
            document.getElementById("accountForContainer").style.display = "block";
        }

        if (item.userType.userTypeId === 2) {
            $("#accountFor").empty();
            const accountFor = $("#accountFor");
            $.each(patients, function(index, item) {
                const fullName = [item.firstName, item.middleName, item.lastName]
                    .filter(item => item !== null)
                    .join(" ")
                accountFor.append(new Option(fullName, item.id));
            });
            $("#accountFor").val(item.person.personId);
        } else if (item.userType.userTypeId === 3) {
            $("#accountFor").empty();
            const accountFor = $("#accountFor");
            $.each(doctors, function(index, item) {
                const fullName = [item.firstName, item.middleName, item.lastName]
                    .filter(item => item !== null)
                    .join(" ")
                accountFor.append(new Option(fullName, item.personId));
            });
            console.log(item);
            $("#accountFor").val(item.person.personId);
        }
        $('#myModal').modal("show");
    });

    $("#newUserButton").on("click", () => {
        document.getElementById("form").reset();
        document.getElementById("idGroup").style.display = "none";
        document.getElementById("updateButtonContainer").style.display = "none";
        document.getElementById("addButtonContainer").style.display = "block";
        document.getElementById("accountForContainer").style.display = "none";
        document.getElementById("isLockedContainer").style.display = "none";
        $("#myModal").modal("show");
    });

    $("#addButton").on("click", () => {
        addNewUser();
        $("#myModal").modal("hide");
    });

    $("#updateButton").on("click", () => {
        updateSelectedUser();
        $("#myModal").modal("hide");
    });

    $("#deleteButton").on("click", () => {
        deleteSelectedUser();
        $("#myModal").modal("hide");
    });

    $("#userType").on("change", function() {
        const accountId = document.getElementById("id").value;
        const userType = $("#userType").val();
        if (userType == 1) {
            document.getElementById("accountForContainer").style.display = "none";
        } else {
            document.getElementById("accountForContainer").style.display = "block";
        }

        if (userType == 2) {
            $("#accountFor").empty();
            const accountFor = $("#accountFor");
            $.each(patients, function(index, item) {
                const fullName = [item.firstName, item.middleName, item.lastName]
                    .filter(item => item !== null)
                    .join(" ")
                accountFor.append(new Option(fullName, item.id));
            });
        } else if (userType == 3) {
            $("#accountFor").empty();
            const accountFor = $("#accountFor");
            $.each(doctors, function(index, item) {
                const fullName = [item.firstName, item.middleName, item.lastName]
                    .filter(item => item !== null)
                    .join(" ")
                accountFor.append(new Option(fullName, item.personId));
            });
        }
    });

    function addNewUser() {
        const username = document.getElementById("userName").value;
        const userType = document.getElementById("userType").value;
        const password = document.getElementById("password").value;
        const isLocked = $("#isLocked").prop("checked") ? 1 : 0;

        const user = {
            "username": username,
            "user_type_id": userType,
            "password": password,
            "isLocked": isLocked
        }

        const personId = $("#accountFor").val();
        if (userType != 1) {
            user.person_id = personId;
        }

        const url = "http://localhost:8080/miuclinical/api/usercontroller";
        const request = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams(user)
        }

        fetch(url, request)
            .then(response => response.json())
            .then(user => {
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function deleteSelectedUser() {
        const id = document.getElementById("id").value;
        const url = `http://localhost:8080/miuclinical/api/usercontroller?user_id=${id}`;
        const request = {
            method: "DELETE"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(user => {
                location.reload();
            })
            .catch(error => console.log(error));
    }

    function updateSelectedUser() {
        const id = document.getElementById("id").value;
        const username = document.getElementById("userName").value;
        const userType = $("#userType").val();
        const password = document.getElementById("password").value;
        const isLocked = $("#isLocked").prop("checked") ? 1 : 0;
        let url = `http://localhost:8080/miuclinical/api/usercontroller?user_id=${id}&username=${username}&user_type_id=${userType}&islock=${isLocked}&password=${password}`;
        const request = {
            method: "PUT"
        }

        fetch(url, request)
            .then(response => response.json())
            .then(user => {
                location.reload();
            })
            .catch(error => console.log(error));
    }

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
            })
            .catch(error => alert(error));
    }

    function getDoctors() {
        fetch("http://localhost:8080/miuclinical/api/doctorcontroller")
            .then(response => response.json())
            .then(items => {
                doctors = items.data;
            })
            .catch(error => alert(error));
    }
}