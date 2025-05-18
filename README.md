# SDH-Projekti3
Përshkrimi
SDH-Projekti3 është një projekt Java që demonstron përdorimin e algoritmit HMAC (Hash-based Message Authentication Code) për komunikim të sigurt ndërmjet një klienti dhe një serveri. Ai është krijuar për qëllime mësimore në fushën e sigurisë së të dhënave.

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
   git clone https://github.com/emriYT/SDH-Projekti3.git

  2. Hap projektin në IntelliJ IDEA ose IDE-në tuaj.

  3. Sigurohu që skedari `config.properties` të ekzistojë dhe përmban një çelës:
   secret_key=MY_SUPER_SECRET_HMAC_KEY_123!

  4. Ekzekuto fillimisht serverin:
   Run -> Run 'HMACServer'

  5. Pastaj ekzekuto klientin:
   Run -> Run 'HMACClient'

  6. Shiko rezultatet në Console.

Shembull Testimi dhe Output

Input nga Klienti:
Mesazh: Pershendetje Server!
Çelësi: KyEshteNjeSekret123

Output në Server:
Serveri është duke pritur mesazhe...
Mesazhi u pranua: Pershendetje Server!
HMAC i pranuar: d2f5a23a... [shkurtuar]
HMAC i llogaritur: d2f5a23a... [shkurtuar]
HMAC valid: PO

Nëse ndodh manipulim i mesazhit ose çelësit:
Serveri është duke pritur mesazhe...
Mesazhi u pranua: Pershendetje Server!
HMAC i pranuar: 000000000000000000000000
HMAC i llogaritur: d2f5a23a...
HMAC valid: JO

Konfigurimi
Skedari config.properties duhet të përmbajë rreshtin e mëposhtëm:
secret_key=KyEshteNjeSekret123
Ky file nuk duhet të ndahet në publik për aplikime reale, pasi përmban informacione sensitive.
