const express = require('express');
const router = express.Router();
const mongoose = require("mongoose");
const request = require("request");

const { v4: uuidv4 } = require('uuid');




const User = require("../models/user");
const { ResourceNotFoundError, InternalError } = require('./errors.js');

//Erhalte alle User      //Brauchen wir  zum überprüfen (als programmierer) => Noch in Rest hinzufügen
router.get("/", (req, res, next) => {

    const query = req.query; // Falls Qury parameter angegeben werden wird gefiltert

    User.find(query)  // Direkt mit Query da wenn keine Query parameter eingegeben ist der "Filter auf null"
        .exec()
        .then(docs => {
            console.log(docs);
            res.status(200).json(docs);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});

//erstelle einen User
router.post("/", (req, res, next) => {

    const user = new User({

        _id: uuidv4(),
        nickname: req.body.nickname,
        vorname: req.body.vorname,
        vorname_sichtbar: req.body.vorname_sichtbar,
        name: req.body.name,
        name_sichtbar: req.body.name_sichtbar,
        strasse: req.body.strasse,
        strasse_sichtbar: req.body.strasse_sichtbar,
        plz: req.body.plz,
        plz_sichtbar: req.body.plz_sichtbar,
        ort: req.body.ort,
        ort_sichtbar: req.body.ort_sichtbar,
        beschreibung: req.body.beschreibung,
        interessen: req.body.interessen,
        faehigkeiten: req.body.faehigkeiten,
        kommunikation: req.body.kommunikation,
        email: req.body.email,
        passwort: req.body.passwort

    });

    user.save()
        .then(result => {
            console.log(result);
            res.status(201).json({
                message: "Handling POST requests to /users",
                createdUser: result
            });
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});

//Erhalte bestimmten User
router.get("/:userId", (req, res, next) => {

    const id = req.params.userId;
    User.findById(id)
        .exec()
        .then(doc => {
            console.log("From database", doc);
            if (doc) {
                res.status(200).json(doc);
            } else {
                res
                    .status(404)
                    .json({ message: "No valid entry found for provided ID" });
            }
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({ error: err });
        });

});

//Lösche bestimmten User
router.delete("/:userId", (req, res, next) => {

    const id = req.params.userId;
    User.remove({_id: id})
    //Hier muss noch hin was mit dem Projekt passiert, wenn dieser USer eins erstellt hat
        .exec()
        .then(result => {
            res.status(200).json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });

        });
});

//Bearbeite/aktualisiere bestimmtem User
//Zum Testen: [{"propName": <Attributname>, "value": <neuer Attributwert>}]
// ZB:  [{"propName": "nickname", "value": "NachUpdate"}]
/*router.put("/:userId", (req, res, next) => {

    const id = req.params.userId;
    const updateOps = {};
    for (const ops of req.body) {
        updateOps[ops.propName] = ops.value;
    }
    User.update({ _id: id }, { $set: updateOps })
        .exec()
        .then(result => {
            console.log(result);
            res.status(200).json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});*/

// ZB: {"nickname": "NachUpdate"}
router.put("/:userId", (req, res, next) => {
    const id = req.params.userId;
   // const updateOps = {};
   // for (const ops of req.body) {
   //     updateOps[ops.propName] = ops.value;
   // }
    User.update({ _id: id }, { $set: req.body })
        .exec()
        .then(result => {
            console.log(result);
            res.status(200).json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});


module.exports = router;