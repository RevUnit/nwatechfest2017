const callInPizzaDelivery = (pizza) => {
  console.log(`calling in pizza ${pizza}`);
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      console.log('pizza delivered');
      resolve(pizza);
    }, 5000);
  });
}

const inviteFriendOver = (friend) => {
  console.log(`calling friend ${friend}`);
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      console.log('friend arrived');
      resolve(friend);
    }, 3000);
  });
}

const hangOut = () => {
  const pizzaOrderPromise = callInPizzaDelivery('pepperoni');
  const friendInvitePromise = inviteFriendOver('Jack');
  Promise.all([pizzaOrderPromise, friendInvitePromise]).then((results) => {
    const [pizza, friend] = results;
    console.log(`Hanging out with ${friend} eating a ${pizza} pizza.`);
  });
  console.log('waiting for food and friend');
}

hangOut();