const params = new URLSearchParams(window.location.search);
const deviceId = params.get("id");

loadDevice();
loadHistory();

/* ------------------------- LOAD DEVICE INFO ------------------------- */
async function loadDevice() {
    try {
        const res = await fetch("https://192.168.0.105:8443/devices/" + deviceId);
        const d = await res.json();

        document.getElementById("devName").textContent = d.name;
        document.getElementById("devSerial").textContent = d.serialNumber;

    } catch (e) {
        console.error("Error loading device:", e);
    }
}

/* ------------------------- LOAD MAINTENANCE HISTORY ------------------------- */
/*async function loadHistory() {
    try {
        const res = await fetch(`https://192.168.0.105:8443/maintenance/device/{deviceId}`);

        if (!res.ok) {
            document.getElementById("maintenanceList").innerHTML = "<p>Δεν βρέθηκαν καταχωρήσεις.</p>";
            return;
        }

        const list = await res.json();

        const container = document.getElementById("maintenanceList");
        container.innerHTML = "";

        list.forEach(entry => {
            const card = document.createElement("div");
            card.className = "card maintenance-card";

            card.innerHTML = `
                <p><strong>Ημερομηνία:</strong> ${entry.maintenanceDate}</p>
                <p><strong>Διάστημα:</strong> <span class="badge badge-${entry.interval.toLowerCase()}">${translateInterval(entry.interval)}</span></p>
                <p><strong>Σημειώσεις:</strong> ${entry.description || "—"}</p>
            `;

            container.appendChild(card);
        });

    } catch (e) {
        console.error(e);
        alert("Σφάλμα φόρτωσης ιστορικού.");
    }
}*/
async function loadHistory() {
    try {
        const url = `https://192.168.0.105:8443/maintenance/device/${deviceId}`;
        console.log("Calling:", url);

        const res = await fetch(url);

        console.log("Status:", res.status);

        const text = await res.text();
        console.log("Raw Response:", text);

        if (!res.ok) {
            document.getElementById("maintenanceList").innerHTML =
                `<p>Σφάλμα backend: status ${res.status}<br>${text}</p>`;
            return;
        }

        const list = JSON.parse(text);

        if (!Array.isArray(list) || list.length === 0) {
            document.getElementById("maintenanceList").innerHTML =
                "<p>Δεν υπάρχουν καταχωρήσεις συντήρησης.</p>";
            return;
        }

        const container = document.getElementById("maintenanceList");
        container.innerHTML = "";

        list.forEach(entry => {
            const card = document.createElement("div");
            card.className = "card";

            card.innerHTML = `
                <p><strong>Ημερομηνία:</strong> ${entry.maintenanceDate}</p>
                <p><strong>Διάστημα:</strong> ${entry.interval}</p>
                <p><strong>Σημειώσεις:</strong> ${entry.description ?? "—"}</p>
            `;

            container.appendChild(card);
        });

    } catch (e) {
        console.error("JS Error:", e);
        document.getElementById("maintenanceList").innerHTML =
            "<p>Σφάλμα φόρτωσης ιστορικού.</p>";
    }
}




/* ------------------------- ADD NEW MAINTENANCE ------------------------- */
function newMaintenance() {
    window.location.href = "maintenance.html?id=" + deviceId;
}

/* ------------------------- INTERVAL TRANSLATION ------------------------- */
function translateInterval(i) {
    switch (i) {
        case "DAILY": return "Ημερήσια";
        case "MONTHLY": return "Μηνιαία";
        case "SEMI_ANNUAL": return "Εξαμηνιαία";
        case "ANNUAL": return "Ετήσια";
        default: return i;
    }
}
