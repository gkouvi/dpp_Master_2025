const params = new URLSearchParams(window.location.search);
const deviceId = params.get("id");

async function submitMaintenance() {

    const payload = {
        deviceId: deviceId,
        description: document.getElementById("desc").value,
        technicianName: document.getElementById("technician").value,
        date: document.getElementById("date").value
    };

    try {
        const res = await fetch("https://192.168.0.105:8443/maintenance/add", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            alert("Η συντήρηση καταχωρήθηκε!");
            window.location.href = "device.html?id=" + deviceId;
        } else {
            alert("Σφάλμα: " + res.status);
        }

    } catch (err) {
        alert("Σφάλμα: " + err);
    }
}



async function createMaintenance() {

    const interval = document.getElementById("intervalSelect").value;
    const desc = document.getElementById("notesInput").value;
    const technician = document.getElementById("technician").value;
    const date = document.getElementById("date").value;

    if (!interval) {
        alert("Παρακαλώ επιλέξτε διάστημα συντήρησης.");
        return;
    }

    if (!date) {
        alert("Παρακαλώ επιλέξτε ημερομηνία.");
        return;
    }

    const payload = {
        deviceId: Number(deviceId),
        maintenanceDate: date,
        interval: interval,
        description: desc,
        technician: technician
    };

    console.log("Sending payload:", payload);

    try {
        const res = await fetch("https://192.168.0.105:8443/maintenance", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const text = await res.text();
        console.log("Backend Response:", text);

        if (!res.ok) {
            alert("Σφάλμα καταχώρησης:\n" + text);
            return;
        }

        alert("✔ Η συντήρηση καταχωρήθηκε με επιτυχία!");
        window.location.href = "maintenance-history.html?id=" + deviceId;

    } catch (e) {
        alert("Σφάλμα σύνδεσης:\n" + e);
    }
}

