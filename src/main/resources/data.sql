INSERT INTO dpp_site (name, region, coordinates) VALUES ('Site A', 'Athens', '37.9838, 23.7275');
INSERT INTO building (name, address, site_id)
VALUES ('Building A', 'Ακτιο',  1);

INSERT INTO device (id, name, type, location, installation_date, subsystem_id)
VALUES (3, 'Main Camera', 'Camera', 'Roof', '2024-01-01', 1);
INSERT INTO subsystem (type, building_id) VALUES ('Camera Group A', 1);
