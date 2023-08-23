package exceptions

import models.ProductModelCommand

class UnknownProductModelCommand(command: ProductModelCommand) : Exception("Wrong command $command at mapping toTransport stage")