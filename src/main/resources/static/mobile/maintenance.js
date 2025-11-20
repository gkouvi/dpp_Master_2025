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
        const res = await fetch("http://localhost:8080/maintenance/add", {
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
