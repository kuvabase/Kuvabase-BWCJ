##### A create wallet flow includes two steps:
1. Create a new wallet
2. Join the wallet in creation to a copayer

##### Creating new wallet step.
To create new wallet use `CreateWalletUseCase.java` it will create new wallet, post it to the Bitcore-wallet-service `POST v2/wallets/` and return a wallet id.

To create a new wallet a `CreateWalletUseCase.java` does next:
1. Constructs a seed from a "BIP39" mnemonic code with an empty passphrase.

To post a new wallet to a BWS a `CreateWalletUseCase.java` does next:
1. Generates a new deterministic key (master private key) from the given seed. 
2. Derives a child from the master private key using "BIP44" derivation strategy (xPrivKey).
3. Get a serialized public key from the "xPrivKey" and encode it with "Base58" algorithm (xPubKey).
4. Generate a "CoapyerId" by encoding of the "xPubKey" using "SHA-256" and "Base16" algorithms.
5. Generates an entirely new keypair (WalletPrivateKey).
6. Calculates the SHA-256 hash of the given a private key from the WalletPrivateKey and encodes it using "Base58" algorithm (SharedEncryptingKey).
7. Uses a `SjclMessageEncryptor.java` which uses a **sjcl.js** java-script library to encrypt 'Personal Wallet' phrase and a "SharedEncryptingKey" as a key(name).

##### Join the wallet in creation to a copayer step.
To join the wallet in creation to a copayer use `JoinWalletInCreationUseCase.java` it will create a join wallet request, post it to a BWS server `POST v2/wallets/{wallet_id}/copayers` and return a server response `IJoinWalletResponse.java` which will contain a "CopayerId" and `IWalletCore.java` with a wallet info.

To join the wallet a `JoinWalletInCreationUseCase.java` does next:
1. Derives a child from the master private key using "m/1`/0" derivation strategy (requestDerivation)
2. Get encoded (“Base16”) public key from a "requestDerivation" (requestPubKey).
3. Calculates the SHA-256 hash of the private key from a "requestDerivation" and encode it using “Base16” algorithm (entropySource).
4. Calculates the "HmacSHA256" hash of an "entropySource" using a 'personalKey' phrase as a key, encodes the given bytes as a base58 string (personalEncryptingKey).
5. Uses a `SjclMessageEncryptor.java` which uses a **sjcl.js** java-script library to encrypt 'me' phrase and a "SharedEncryptingKey" as a key (encCopayerName).
6. Uses a `SjclMessageEncryptor.java` which uses a **sjcl.js** java-script library to encrypt a json representation of an object with one filed which is a private key of a "walletPrivKey" and a "personalEncryptingKey" as a key (encCustomData).
7. Joins "encCopayerName", "xPubKey" and "requestPubKey" in one string with "|" splitter to generate a message for copayerSignature.
8. Double "Sha256" hash the message, signs the message by a "privKey" using "ECDSA". Getting the r and s values using low S (copayerSignature).
9. Generates a request and post it to a server 
`new JoinWalletRequest(copayerSignature, encCustomData, encCopayerName, requestPubKey, walletId, xPubKey);`.