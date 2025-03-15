package com.example.dogadaji;

public sealed interface ZabavniDogadaj permits Kviz, Putovanje, Zabava {
    // napravit da se pokrecu niti koje simuliraju drustvene igre
    void tombola();
    void vukodlak();
}
