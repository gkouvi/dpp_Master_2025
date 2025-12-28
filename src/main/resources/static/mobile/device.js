const params = new URLSearchParams(window.location.search);
const deviceId = params.get("id");
/******* GLOBAL API BASE URL ********/
const API = location.origin.includes("8443")
    ? location.origin
    : "https://" + location.hostname + ":8443";

function getToken() {
    const t = localStorage.getItem("jwt");
    if (!t) {
        window.location.href = "login.html";
        return null;
    }
    return t;
}


loadDevice();
loadEnvironmental();
loadAlerts();


async function loadDevice() {
    //const url = "http://localhost:8080/devices/" + deviceId;
    const url = "https://192.168.0.105:8443/devices/" + deviceId;

    try {
        const res = await fetch(url, {
            headers: { "Authorization": "Bearer " + getToken() }
        });
        const data = await res.json();

        document.getElementById("name").textContent = data.name;
        document.getElementById("type").textContent = data.type;
        document.getElementById("serial").textContent = data.serialNumber;
        document.getElementById("instDate").textContent = data.installationDate;
        document.getElementById("firmware").textContent = data.firmwareVersion;
        document.getElementById("status").textContent = data.status;
        document.getElementById("buildingId").textContent = data.buildingId;

        if (data.qrBase64) {
            const img = document.getElementById("qrImg");
            img.src = "data:image/png;base64," + data.qrBase64;
            img.style.display = "block";
        }

    } catch (err) {
        alert("Error loading device: " + err);
    }
}

/* ============== LOAD ENVIRONMENTAL INFO ============== */
async function loadEnvironmental() {
    try {
        //const url = "http://localhost:8080/environment/" + deviceId;
        const url = "https://192.168.0.105:8443/environment/device/" + deviceId;

        const res = await fetch(url, {
            headers: { "Authorization": "Bearer " + getToken() }
        });

        if (!res.ok) {
            document.getElementById("materials").textContent = "—";
            document.getElementById("recycling").textContent = "—";
            document.getElementById("hazardous").textContent = "—";
            document.getElementById("recyclability").textContent = "—";
            document.getElementById("weight").textContent = "—";
            return;
        }

        const env = await res.json();

        document.getElementById("materials").textContent = env.materialsComposition ?? "—";
        document.getElementById("recycling").textContent = env.recyclingInstructions ?? "—";
        document.getElementById("hazardous").textContent = env.hazardousMaterials ?? "—";
        document.getElementById("weight").textContent = env.deviceWeightKg ?? "—";

        const badge = document.getElementById("recyclability");
        badge.textContent = (env.recyclabilityPercentage ?? 0) + "%";

        if (env.recyclabilityPercentage >= 80) badge.classList.add("badge-green");
        else if (env.recyclabilityPercentage >= 40) badge.classList.add("badge-orange");
        else badge.classList.add("badge-red");

    } catch (err) {
        console.error("ENV ERROR", err);
    }
}


async function loadAlerts() {
    try {
        const url = `${API}/alerts/device/${deviceId}`;
        const res = await fetch(url, {
            headers: { "Authorization": "Bearer " + getToken() }
        });

        if (!res.ok) {
            document.getElementById("alertsList").innerHTML =
                "<p class='empty'>Δεν υπάρχουν ειδοποιήσεις.</p>";
            return;
        }

        const alerts = await res.json();
        const container = document.getElementById("alertsList");
        container.innerHTML = "";

        alerts.forEach(a => {
            const card = document.createElement("div");
            card.className = "alert-card";

            card.innerHTML = `
                <div class="alert-header">
                    <span class="alert-date">${a.dueDate}</span>
                    <span class="alert-status ${a.status === 'Offline' ? 'offline' : 'online'}">
                        ${a.status}
                    </span>
                </div>

                <div class="alert-msg">${a.message}</div>
            `;

            container.appendChild(card);
        });

    } catch (err) {
        console.error("ALERT LOAD ERROR:", err);
        document.getElementById("alertsList").innerHTML =
            "<p class='empty'>Σφάλμα φόρτωσης.</p>";
    }
}



function addMaintenance() {
    window.location.href = "maintenance.html?id=" + deviceId;
}

function viewMaintenance() {
    window.location.href = "maintenance-history.html?id=" + deviceId;
}

function editEnvironmental() {
    window.location.href = "environment-edit.html?id=" + deviceId;
}
function editEnvironmental() {
    window.location.href = "environment-edit.html?deviceId=" + deviceId;
}

