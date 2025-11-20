const params = new URLSearchParams(window.location.search);
const deviceId = params.get("id");

loadDevice();

async function loadDevice() {

    const url = "http://localhost:8080/devices/" + deviceId;

    try {
        const res = await fetch(url);
        const data = await res.json();

        document.getElementById("name").textContent = data.name;
        document.getElementById("type").textContent = data.type;
        document.getElementById("serial").textContent = data.serialNumber;
        document.getElementById("instDate").textContent = data.installationDate;
        document.getElementById("firmware").textContent = data.firmwareVersion;
        document.getElementById("status").textContent = data.status;
        document.getElementById("buildingId").textContent = data.buildingId;

        // show QR if exists
        if (data.qrBase64) {
            const img = document.getElementById("qrImg");
            img.src = "data:image/png;base64," + data.qrBase64;
            img.style.display = "block";
        }

    } catch (err) {
        alert("Error loading device: " + err);
    }
}

function addMaintenance() {
    window.location.href = "maintenance.html?id=" + deviceId;
}
