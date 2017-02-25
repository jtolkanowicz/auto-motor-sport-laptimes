# auto-motor-sport-laptimes

## Description

This is website crawler which uses [Geb](http://www.gebish.org/) to read data from 
[Auto motor und sport](http://www.auto-motor-und-sport.de/) website, and save it in local Mongodb. It uses only Chrome 
to perform this crawling. This project used [Example Geb and Gradle project](https://github.com/geb/geb-example-gradle)
 as it base.

## Gathered data

Crawler gathers data from [Supertests](http://www.auto-motor-und-sport.de/supertests/). This tests are conducted on 
[Nurburgring](https://en.wikipedia.org/wiki/N%C3%BCrburgring) Nordschleife ("North Loop") and on 
[Hockenheimring](https://en.wikipedia.org/wiki/Hockenheimring) short course tracks. Gathered data contains:

* Car data - make, model, production years, gearbox type, layout (AWD, FWD, RWD), weight, engine power, engine torque
* Test info - driver, test date, url
* Test results - Nordschleife laptime, Hockenheim laptime, acceleration times 0-100 km/h and 0-200 km/h

## Usage

Crawler has three scripts:

* importLinks - it goes through paged list of supertests and saves url's
* verifyLinks - it goes through saved url's and check's which supertests have tests results (some supertests are testing
 SUV/terrain cars, and don't contain track data; some tests of sports cars don't have relevant data in test results but 
 in text, see below in _Problems_)
* readTestData - it goes through verified links, checks if data exists once more and reads it

You run them in this order:

    gradle importLinks
    gradle verifyLinks
    gradle readTestData

Script _importLinks_ drops data from Mongo and inserts fresh one to collection _links_. _verifyLinks_ only updates data.
 _readTestData_ inserts new data, and can be restarted if it fails. It starts where it stopped before. If you want to 
 read data from scratch you must drop collection named _results_.

## Why gather data this data

Data from [Supertests](http://www.auto-motor-und-sport.de/supertests/) especially Nordschleife laptimes are quite 
reliable. Most of tests are performed by one driver, [Horst von Saurma](https://en.wikipedia.org/wiki/Horst_von_Saurma) 
and cars are in factory specification including tires and equipment. Automakers have their 'own definition' of 
factory/street-legal specification.
 
## Why this technology stack
 
I wanted to become more familiar with [Geb](http://www.gebish.org/) and use Mongodb which I hadn't chance to use before.

## Problems

Tests below are problematic:
* [McLaren 650s Spider - 9266605](http://www.auto-motor-und-sport.de/supertest/supertest-mclaren-650s-spider-nuerburgring-und-hockenheim-9266605.html) - Nurburgring data in test section
* [BMW M5 Competition - 8911761](http://www.auto-motor-und-sport.de/supertest/bmw-m5-competition-leistungsexplosion-der-sportlimousine-8911761.html) - Nurburgring data on image and in text
* [Audi TT RS - 1747833](http://www.auto-motor-und-sport.de/supertest/audi-tt-rs-mit-340-ps-im-supertest-coupe-mit-fuenfzylinder-turbo-on-track-1747833.html) - Nurburgring data on image
* [Ferrari 599 GTB - 1031324](http://www.auto-motor-und-sport.de/supertest/ferrari-599-gtb-fiorano-im-test-auf-der-nordschleife-ferrari-gran-turismo-mit-enzo-genen-1041324.html) - Nurburgring data on image and in text
* [Porsche 911 GT2 - 1031363](http://www.auto-motor-und-sport.de/supertest/porsche-911-gt2-auf-nordschleife-und-hockenheimring-1041363.html) - Nurburgring data on image and in text
* [Nissan GT-R - 1347840](http://www.auto-motor-und-sport.de/supertest/nissan-gt-r-objektive-nordschleifen-rundezeit-des-486-ps-japaners-gtr-1347840.html) - Nurburgring data on image and in text
* [Lamborghini Gallardo LP560-4 - 1031516](http://www.auto-motor-und-sport.de/supertest/lamborghini-gallardo-lp-560-4-auf-nordschleife-und-hockenheimring-1041516.html) - Nurburgring data on image and in text
* [Ferrari F430 Scuderia - 1031447](http://www.auto-motor-und-sport.de/supertest/ferrari-f430-scuderia-im-supertest-ist-der-gestaerkte-italiener-ein-supertalent-1041447.html) - Nurburgring data on image and in text
* [Audi RS4 - 1036092](http://www.auto-motor-und-sport.de/supertest/raum-zu-glauben-supertest-10-2000-audi-rs4-1036092.html) - Nurburgring data in test section
* [Lamborghini Murcielago LP640 - 1041040](http://www.auto-motor-und-sport.de/supertest/lamborghini-murcielago-lp-640-auf-nordschleife-und-hockenheimring-1041040.html) - Nurburgring data on image and in text
* [Porsche 911 GT3 - 1030952](http://www.auto-motor-und-sport.de/supertest/porsche-911-gt3-auf-der-rennstrecke-nuerburgring-hockenheim-1040952.html) - Nurburgring data on image and in text
* [Renault Megane Sport Trophy - 1041029](http://www.auto-motor-und-sport.de/supertest/renault-megane-sport-trophy-sport-auto-edition-im-supertest-der-megane-sport-trophy-sport-auto-edition-auf-der-nordschleife-1041029.html) - no data at all
* [Aston Martin V12 Vanquish - 1036049](http://www.auto-motor-und-sport.de/supertest/aston-martin-v12-vanquish-im-supertest-test-des-aston-martin-v12-vanquish-auf-der-nordschleife-1036049.html) - no data at all
* [Pagani Zonda S - 1036024](http://www.auto-motor-und-sport.de/supertest/pagani-zonda-s-supertest-1036024.html) - Nurburgring data in text
* [Ferrari 550 Maranello - 1031307](http://www.auto-motor-und-sport.de/supertest/ferrari-550-maranello-feiner-italiener-1041307.html) - no data at all
Solution: change reading data, add flag to force parsing even if there is no relevant data, add Nurburgring results 
manually

Other issues
* Porsche 918 has got power only from combustion engine
* there is no info about tires in tests results, only in text
Solution: add info about tires manually (currently need to check 167 articles) ??