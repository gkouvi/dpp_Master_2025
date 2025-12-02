const API = "https://192.168.0.105:8443";
let deviceId = null;

window.onload = () => {
    const params = new URLSearchParams(location.search);
    deviceId = params.get("deviceId");

    loadEnvironmentalEdit();
};

/* ================ LOAD EXISTING DATA ================== */
async function loadEnvironmentalEdit() {
    try {
        const res = await fetch(`${API}/environment/${deviceId}`);

        if (!res.ok) return;

        const env = await res.json();

        document.getElementById("materials").value = env.materialsComposition ?? "";
        document.getElementById("recycling").value = env.recyclingInstructions ?? "";
        document.getElementById("hazardous").value = env.hazardousMaterials ?? "";
        document.getElementById("recyclability").value = env.recyclabilityPercentage ?? "";
        document.getElementById("weight").value = env.deviceWeightKg ?? "";

    } catch (e) {
        console.error("ENV LOAD ERR", e);
    }
}

/* ================ SAVE DATA ================== */
async function saveEnvironmental() {
    const payload = {
        deviceId: Number(deviceId),
        materialsComposition: document.getElementById("materials").value,
        recyclingInstructions: document.getElementById("recycling").value,
        hazardousMaterials: document.getElementById("hazardous").value,
        recyclabilityPercentage: Number(document.getElementById("recyclability").value),
        deviceWeightKg: Number(document.getElementById("weight").value)
    };

    try {
        const res = await fetch(`${API}/environment`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        if (!res.ok) {
            alert("Αποτυχία αποθήκευσης");
            return;
        }

        alert("Οι περιβαλλοντικές πληροφορίες αποθηκεύτηκαν.");
        history.back();

    } catch (e) {
        console.error("SAVE ENV ERROR", e);
        alert("Σφάλμα κατά την αποθήκευση.");
    }
}
