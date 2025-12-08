# 🚗 Oto Galeri Projesi

Gallerist, Arac satısı için hazırlanmıs bir backend projesidir.Herhangi bir isterden api lar üzerinden servis saglar
USD ve TR olarak para birimlerini anlık merkez bankası verilerine göre kur kontrolu yapıp donusumunu gerceklestirir.
Müsteri accountunda arac bedeline yeteri kadar para varsa satıs işlemi gercekleştirilir

---

## 🚀 Özellikler

- 🚗 Müsteri kayıt ve araç satısı
- 🚨 Exception Mimarisi
- 💰 USD ve TR olarak para birimlerini anlık merkez bankası verilerine göre kur kontrolu yapıp donusumunu gerceklestirir.
- 🔒 JWT ile güvenli giriş sistemi
- 🌐 Swagger dokümantasyonu

---

## 🛠️ Teknolojiler

| Katman | Teknoloji                  |
|--------|----------------------------|
| Backend | Java 20, Spring Boot 3.4.4 |
| Veritabanı | PostgreSQL 14              |
| Güvenlik | Spring Security, JWT       |
| Dokümantasyon | Swagger/OpenAPI            |
| Test | JUnit                      |

---

## 📸 Ekran Görüntüleri
![image](https://github.com/user-attachments/assets/f6b6b30d-5c85-49d2-9a23-414e908ea011)
![image](https://github.com/user-attachments/assets/af8cead4-6550-42e4-b0f9-68f5c9fd66cb)
![image](https://github.com/user-attachments/assets/d3d9daea-26a2-455d-b997-8a3742444882)
![image](https://github.com/user-attachments/assets/71a4e88d-3967-4525-8b70-5e4c32081a48)
![img.png](img.png)


## Kurulum

### 📥 Projeyi Klonla

```bash
git clone https://github.com/erdoganp/Gallerist-Projesi.git

📦 Bağımlılıkları Kurun
Proje türüne göre:

Java (Maven):
./mvnw clean install


▶️ Projeyi Başlatın

./mvnw spring-boot:run

