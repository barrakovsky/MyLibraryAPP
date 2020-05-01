const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.setUserCustomClaims = functions.https.onCall((data, context) =>{
    return admin.auth().getUserByEmail(data.email).then(user=> {
        const claims = {};
        claims['admin'] = false;
        return admin.auth().setCustomUserClaims(user.uid, claims);
    }).then(() =>{
        return{
        message: 'Success! ${data.email} is a user'
        }
    }).catch(err => {
    return err;
    });
});


/*
exports.assignAdminClaim = functions.firestore
    .document('tempoAssignClaim/{tempoId}')
    .onCreate((snap, context) => {

        const claims = {};
        claims['admin'] = true;


        return admin.auth().setCustomUserClaims('HeHPkCgTraerQZzupSMTbufnDfH3', claims);
    });
*/
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
