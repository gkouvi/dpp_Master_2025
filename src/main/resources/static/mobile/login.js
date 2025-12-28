const API = "https://192.168.0.105:8443";

async function doLogin() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const res = await fetch("/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    });

    if (!res.ok) {
        alert("Invalid credentials");
        return;
    }

    const data = await res.json();
    localStorage.setItem("jwt", data.token);
    window.location.href = "/mobile/index.html";
}


function logout() {
    localStorage.removeItem("token");
    window.location = "login.html";
}

function getAuthHeaders() {
    return {
        "Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-Type": "application/json"
    };
}

function ensureLoggedIn() {
    if (!localStorage.getItem("token")) {
        window.location = "login.html";
    }
}
