# **🚀 Hipicon Backend Case Study (POC)**

Bu çalışma, Hipicon'un ürün yönetim süreçlerini ve güvenli erişim mekanizmalarını test eden bir Backend API POC (Proof of Concept) çalışmasıdır.

## **📋 POC Gereksinimleri ve Uygulama Detayları**

### **1\. Kimlik Doğrulama (HIPI-1)**

* **Giriş:** admin@hipicon.com / hipicon123 bilgileriyle JWT alınır.  
* **Güvenlik:** Tüm ürün operasyonları Authorization: Bearer \<token\> başlığı gerektirir.
* **Not:** Kullanıcı verileri data.sql dosyası ile veritabanına import edilmiştir. 

### **2\. Ürün Yönetimi (HIPI-2)**

* **Otomatik Statü:** Yeni eklenen ürünler varsayılan olarak PENDING statüsündedir.  
* **Güncelleme Döngüsü:** Bir ürün güncellendiğinde statüsü otomatik olarak PENDING'e çekilir.  
* **Onay Mekanizması:** /approve endpoint'i ile ürün ACTIVE hale getirilir.  
* **Ürün Onay Takip Job:** Her 5 dakikada bir çalışan sistem görevi, `PENDING` durumundaki ürünleri tasarımcı (sellerName) bazında gruplayarak sayılarını sistem loglarına yazdırır.

## **🔌 API Dokümantasyonu**

### **Kimlik Doğrulama (Auth)**

| Metot | URL | Açıklama |
| :---- | :---- | :---- |
| POST | /api/auth/login | JWT Token üretir. |

### **Ürün Yönetimi (Üyelik/Token Gerektirir)**

| Metot | URL | Parametreler (JSON/Query) |
| :---- | :---- | :---- |
| POST | /api/products | name, sellerName, price, photoUrls\[\], description |
| PUT | /api/products | id, name, sellerName, price, photoUrls\[\], description |
| GET | /api/products | ProductFilterDTO alanları (Query Param) |
| PUT | /api/products/{id}/approve | Ürünü ACTIVE yapar. |
| PUT | /api/products/{id}/deactivate | Ürünü DEACTIVE yapar. |

## **🧪 Test Senaryoları (CURL)**

### **1\. Giriş Yap ve Token Al**

curl -X POST "https://hipicon-backend-case-study-9741a53883fc.herokuapp.com/api/auth/login" \
-H "Content-Type: application/json" \
-d '{ "email": "admin@hipicon.com", "password": "hipicon123" }'

### **2\. Yeni Ürün Oluştur**

curl -X POST "https://hipicon-backend-case-study-9741a53883fc.herokuapp.com/api/products" \
-H "Authorization: Bearer <TOKEN>" \
-H "Content-Type: application/json" \
-d '{ "name": "Eames Lounge Chair", "sellerName": "Modern Tasarım Ofisi", "price": 45000.00, "photoUrls": ["https://cdn.hipicon.com/p/1.jpg"], "description": "Klasik tasarım, hakiki deri." }'

### **3\. Ürünleri Filtrele**

curl -G "https://hipicon-backend-case-study-9741a53883fc.herokuapp.com/api/products" \
--data-urlencode "status=PENDING" \
--data-urlencode "sellerName=Modern Tasarım Ofisi" \
--data-urlencode "minPrice=1000" \
--data-urlencode "maxPrice=50000" \
--data-urlencode "pageNumber=0" \
--data-urlencode "pageSize=10" \
-H "Authorization: Bearer <TOKEN>"

### **4\. Sadece Fiyat Aralığı ile Filtrele**

curl -X GET "https://hipicon-backend-case-study-9741a53883fc.herokuapp.com/api/products?minPrice=20000&maxPrice=60000" \
-H "Authorization: Bearer <TOKEN>"

### **5\. Ürünü Onayla**

curl -X PUT "https://hipicon-backend-case-study-9741a53883fc.herokuapp.com/api/products/1/approve" \
-H "Authorization: Bearer <TOKEN>"

### **6\. Ürünü Güncelle**

curl -X PUT "https://hipicon-backend-case-study-9741a53883fc.herokuapp.com/api/products" \
-H "Authorization: Bearer <TOKEN>" \
-H "Content-Type: application/json" \
-d '{ "id": 1, "name": "Eames Lounge Chair - V2", "sellerName": "Modern Tasarım Ofisi", "price": 48000.00, "photoUrls": ["https://cdn.hipicon.com/p/1.jpg"], "description": "Fiyat güncellendi." }'

## **⚙️ Kurulum ve Çalıştırma**

**Projenin derlenmesi:**

./mvnw clean install

**Uygulamanın başlatılması:**

java \-jar target/hipicon-backend-case-study-0.0.1-SNAPSHOT.jar  
