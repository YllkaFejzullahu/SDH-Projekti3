# SDH-Projekti3
-Përshkrimi

SDH-Projekti3 është një projekt Java që demonstron përdorimin e algoritmit HMAC (Hash-based Message Authentication Code) 
për komunikim të sigurt ndërmjet një klienti dhe një serveri. 
Ai është krijuar për qëllime mësimore në fushën e sigurisë së të dhënave.

Ky projekt ilustron:

Si të gjenerosh dhe verifikosh nënshkrime HMAC në Java.
Si të ngarkosh konfigurime nga një file të jashtëm.
Strukturimin e një aplikacioni të thjeshtë me module të ndara.

-Teknologjitë e Përdorura

Java (JDK 8 ose më i lartë),

IntelliJ IDEA si ambient zhvillimi,

Algoritmi HMAC-SHA256


-Si të Ekzekutosh Projektin Lokalisht

1. Kërkesat paraprake

Java SDK i instaluar (java -version)

IntelliJ IDEA ose IDE tjetër për Java

2. Hapat

*Klono këtë repo:
   git clone https://github.com/emriYT/SDH-Projekti3.git

*Hap projektin në IntelliJ IDEA.

*Shko te Run -> Run 'HMACServer' për të nisur serverin.
Pastaj Run -> Run 'HMACClient' për të dërguar mesazhin.

-Si Funksionon HMAC në këtë Projekt

Serveri ka një çelës sekret të ruajtur në config.properties.

Klienti gjeneron një mesazh dhe e nënshkruan atë me HMAC-SHA256 duke përdorur çelësin.

Serveri merr mesazhin dhe HMAC-in, dhe verifikon nënshkrimin për të siguruar që mesazhi nuk është ndryshuar.


-Shembull Testimi

Ky është një shembull testimi i thjeshtë për të provuar funksionimin e sistemit:

Input nga Klienti:

Mesazh: Pershendetje Server!

Çelësi: KyEshteNjeSekret123

Output nga Serveri:

Mesazhi u pranua: Pershendetje Server!

HMAC valid: PO

Nëse HMAC nuk përputhet:

Mesazhi u pranua: Pershendetje Server!

HMAC valid: JO

Ky test tregon që komunikimi është i sigurt dhe manipulimi i mesazhit ose çelësit shkakton dështim të verifikimit.

-Konfigurimi

Konfigurimi ndodhet në skedarin config.properties. Shembull:

secret_key=KyEshteNjeSekret123

Ky file nuk duhet të dërgohet në publik nëse projekti bëhet real!


