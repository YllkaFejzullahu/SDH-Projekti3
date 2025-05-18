# SDH-Projekti3

Përshkrimi
SDH-Projekti3 është një projekt Java që demonstron përdorimin e algoritmit HMAC (Hash-based Message Authentication Code)
për komunikim të sigurt ndërmjet një klienti dhe një serveri. Ai është krijuar për qëllime mësimore në fushën e sigurisë
së të dhënave.

Ky projekt ilustron:
Si të gjenerosh dhe verifikosh nënshkrime HMAC në Java.
Si të ngarkosh konfigurime nga një file të jashtëm.
Strukturimin e një aplikacioni të thjeshtë me module të ndara.

Teknologjitë e Përdorura

Java (JDK 8 ose më i lartë)
IntelliJ IDEA si ambient zhvillimi
Algoritmi HMAC-SHA256

Si të Ekzekutosh Projektin Lokalisht

1. Kërkesat paraprake
   Java SDK i instaluar (java -version duhet të japë një version më të lartë se 1.8)
   IntelliJ IDEA ose IDE tjetër për Java

2. Hapat
1. Klono këtë repo:
   git clone https://github.com/YllkaFejzullahu/SDH-Projekti3.git

2. Hap projektin në IntelliJ IDEA ose IDE-në tuaj.

3. Sigurohu që skedari `config.properties` të ekzistojë dhe përmban një çelës:
   secret_key=Kyeshtenjesekret1

4. Ekzekuto fillimisht serverin:
   Run -> Run 'HMACServer'

5. Pastaj ekzekuto klientin:
   Run -> Run 'HMACClient'

6. Shiko rezultatet në Console.

Shembull Testimi dhe Output

Input nga Klienti:
Mesazh: Pershendetje!
Çelësi: Kyeshtenjesekret1

Kur bëhet run te Klienti:

=== HMAC CLIENT ===  
2025-05-18 22:01:02.739 [INFO] Secret key successfully loaded by client.  
Enter your message: Pershendetje!  
2025-05-18 22:01:09.341 [INFO] Generated HMAC: dee63748824f71ed7162c1de387501120aa393e015a052155a9e121d6c6c9eb6  
Sending message with HMAC: [Pershendetje! | dee63748824f71ed7162c1de387501120aa393e015a052155a9e121d6c6c9eb6]  
2025-05-18 22:01:09.342 [INFO] Connecting to server at localhost:12345  
2025-05-18 22:01:09.343 [INFO] Sending message with HMAC: [2025-05-18T20:01:09.300065700Z::Pershendetje!::dee63748824f71ed7162c1de387501120aa393e015a052155a9e121d6c6c9eb6]  
2025-05-18 22:01:09.344 [INFO] Message with timestamp and HMAC sent to server.  
2025-05-18 22:01:09.378 [INFO] Server response: Message verified successfully.  


Output në Server:

==== HMAC SERVER ====  
2025-05-18 22:00:56.633 [INFO] Secret key successfully loaded by server.  
2025-05-18 22:00:56.645 [INFO] Server started and awaiting messages...  
2025-05-18 22:01:09.349 [INFO] Message received with HMAC: [2025-05-18T20:01:09.300065700Z | Pershendetje! | dee63748824f71ed7162c1de387501120aa393e015a052155a9e121d6c6c9eb6]  
2025-05-18 22:01:09.349 [INFO] Validating HMAC...  
2025-05-18 22:01:09.378 [INFO] Message verified successfully. Integrity and authenticity confirmed.  



Konfigurimi
Skedari config.properties duhet të përmbajë rreshtin e mëposhtëm:
secret_key=Kyeshtenjesekret1
Ky file nuk duhet të ndahet në publik për aplikime reale, pasi përmban informacione sensitive.
