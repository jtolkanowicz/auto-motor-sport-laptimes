# auto-motor-sport-laptimes

## Description

This is website crawler which uses [Geb](http://www.gebish.org/) to read data from 
[Auto motor und sport](http://www.auto-motor-und-sport.de/) website, and save it in local Mongodb. It uses 
only Chrome to perform this crawling. This project used 
[Example Geb and Gradle project](https://github.com/geb/geb-example-gradle) as it base.

## Gathered data

Crawler gathers data from [Supertests](http://www.auto-motor-und-sport.de/supertests/). This tests are 
conducted on [Nurburgring](https://en.wikipedia.org/wiki/N%C3%BCrburgring) Nordschleife ("North Loop") and on 
[Hockenheimring](https://en.wikipedia.org/wiki/Hockenheimring) short course tracks. Gathered data contains:

* Car data - make, model, production years, gearbox type, layout (AWD, FWD, RWD), weight, engine power, 
engine torque
* Test info - driver, test date, url
* Test results - Nordschleife laptime, Hockenheim laptime, acceleration times 0-100 km/h and 0-200 km/h

## Usage

Crawler has three scripts:

* importLinks - it goes through paged list of supertests and saves url's
* verifyLinks - it goes through saved url's and check's which supertests have tests results (some supertests
are testing SUV/terrain cars, and don't contain track data; some tests of sports cars don't have relevant
data in test results but in text, see below in _Problems_)
* readTestData - it goes through verified links and reads test data

You run them in this order:

    gradle importLinks
    gradle verifyLinks
    gradle readTestData

Script importLinks drops data from Mongo and insert fresh one to collection _links_. verifyLinks only 
updates data. readTestData inserts new data, and can be restarted if it fails. It starts where it stopped 
before. If you want to read data from scratch you must drop collection named _results_.

## Why gather data this data

Data from [Supertests](http://www.auto-motor-und-sport.de/supertests/) especially Nordschleife laptimes are 
quite reliable. Most of tests are performed by one driver, 
[Horst von Saurma](https://en.wikipedia.org/wiki/Horst_von_Saurma) and cars are in factory specification 
 including tires and equipment. Automakers have their 'own definition' of factory/street-legal specification.
 
## Why this technology stack
 
I wanted to become more familiar with [Geb](http://www.gebish.org/) and use Mongodb which I hadn't chance
to use before.

## Problems

Tests below are problematic:
supertest-mclaren-650s-spider-nuerburgring-und-hockenheim-9266605.html (Nurburgring data in test section)
bmw-m5-competition-leistungsexplosion-der-sportlimousine-8911761.html (Nurburgring data on image and in text)
audi-tt-rs-mit-340-ps-im-supertest-coupe-mit-fuenfzylinder-turbo-on-track-1747833.html (Nurburgring data on image)
ferrari-599-gtb-fiorano-im-test-auf-der-nordschleife-ferrari-gran-turismo-mit-enzo-genen-1041324.html (Nurburgring data on image and in text)
porsche-911-gt2-auf-nordschleife-und-hockenheimring-1041363.html (Nurburgring data on image and in text)
nissan-gt-r-objektive-nordschleifen-rundezeit-des-486-ps-japaners-gtr-1347840.html (Nurburgring data on image and in text)
lamborghini-gallardo-lp-560-4-auf-nordschleife-und-hockenheimring-1041516.html (Nurburgring data on image and in text)
ferrari-f430-scuderia-im-supertest-ist-der-gestaerkte-italiener-ein-supertalent-1041447.html (Nurburgring data on image and in text)
raum-zu-glauben-supertest-10-2000-audi-rs4-1036092.html (Nurburgring data in test section)
lamborghini-murcielago-lp-640-auf-nordschleife-und-hockenheimring-1041040.html (Nurburgring data on image and in text)
porsche-911-gt3-auf-der-rennstrecke-nuerburgring-hockenheim-1040952.html (Nurburgring data on image and in text)
renault-megane-sport-trophy-sport-auto-edition-im-supertest-der-megane-sport-trophy-sport-auto-edition-auf-der-nordschleife-1041029.html (no data at all)
aston-martin-v12-vanquish-im-supertest-test-des-aston-martin-v12-vanquish-auf-der-nordschleife-1036049.html (no data at all)
pagani-zonda-s-supertest-1036024.html (Nurburgring data in text)
ferrari-550-maranello-feiner-italiener-1041307.html (no data at all)

918 has got power only from combustion engine
no info about tires