const cookPizza = (pizza) => {
  console.log('put pizza in oven');
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      console.log('oven timer sounded');
      resolve(pizza);
    }, 5000);
  });
}

const preparePizzaAndOven = () => {
  const pizza = { brand: 'Digiorno' };
  console.log('prepared pizza');
  return pizza;
}

const makeSalad = () => {
  console.log('made salad');
}

const removePizzaAndSliceIt = (pizza) => {
   console.log(`removed ${ cookedPizza.brand } pizza and sliced it`);
}

const makeLunch = () => {
  const pizza = preparePizzaAndOven();
  cookPizza(pizza).then(removePizzaAndSliceIt);
  makeSalad();
}

makeLunch();