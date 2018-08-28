##### A send funds flow includes next steps:
1. Create a new transaction proposal.
2. Add the transaction proposal.
3. Build a transaction.
3. Publish the transaction proposal.
4. Sign the transaction proposal.
5. Broadcast the transaction proposal.

##### Create and add a new transaction proposal
Make a server request `POST v2/txproposals/`, a response will contain info about the transaction proposal (the info is required for the next steps)

To create a new transaction proposal and add it to a server use a `AddNewTxpUseCase.java`.
`AddNewTxpUseCase.execute()` required info about:
- address to send
- satoshis
- message
- dry run
- rate
 
##### Build a transaction.
To build a [transaction](https://bitcoinj.github.io/working-with-transactions) from the transaction proposal and sort it (using an output order recieved in the transaction proposal) use `CopayTransactionUtils.buildTx(ITransactionProposal)` it will return [Transaction.java](https://github.com/HashEngineering/dashj/blob/master/core/src/main/java/org/bitcoinj/core/Transaction.java) with correct sorted outputs and inputs (rigth order is required for BWS).

##### Publish the transaction proposal.
To publish the transaction proposal required to generate a proposal signature, the steps are:

1. Serialize the Transaction to a byte array that conforms to the bitcoin wire protocol using `Transaction.unsafeBitcoinSerialize()`.
2. Encode it using "Base16".
3. Double "Sha256" hash the given String, signs the String by a "privKey" using "ECDSA". Getting the r and s values using low S.

Post a proposal signature to a server `POST v1/txproposals/{tx_id}/publish`
To build a transaction and publish a transaction proposal to a server use `PublishTxpUseCase.java`.

##### Sign the transaction proposal.
To generate transaction signatures required for a server request:
1. Use `Transaction.calculateSignature()` to generate [TransactionSignature.java](https://github.com/HashEngineering/dashj/blob/master/core/src/main/java/org/bitcoinj/crypto/TransactionSignature.java)
2. Each signature need to be encoded to DER and "Base16".
Post signature array to a server `POST v1/txproposals/{tx_id}/signatures`

To generate transaction signatures and post it to a server use `SignTxpUseCase.java`

##### Broadcast the transaction proposal.
To broadcast a transaction proposal make a server request `POST v1/txproposals/{tx_id}/broadcast`.
To broadcast a transaction proposal use `BroadcastTxpUseCase.java`.










