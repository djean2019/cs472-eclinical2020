"use strict";

window.onload = () => {

    document.getElementById("form").addEventListener("submit", function(event) {
        event.preventDefault();
        const username = document.getElementById("inputUsername").value;
        const password = document.getElementById("inputPassword").value;

        const url = "http://localhost:8080/miuclinical/api/userlogincontroller";
        const user = {
            "username": username,
            "password": password
        }
        const request = {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(user)
        }

        fetch(url, request)
            .then(response => response.json())
            .then(user => {
                console.log(user.data[0]);
                const userData = user.data[0];
                const username = userData.username;
                const userId = userData.userId;
                const userType = userData.userType.userTypeId;
                console.log(userData);
                console.log(username);
                console.log(userType);
                console.log(userId);
                document.cookie = `username=${username}`;
                document.cookie = `role=${userType}`;
                document.cookie = `userId=${userId}`;
                window.location = "../index.html";
            })
            .catch(error => alert(error));
    });
}