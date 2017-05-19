import { ExpensesFrontendPage } from './app.po';

describe('expenses-frontend App', () => {
  let page: ExpensesFrontendPage;

  beforeEach(() => {
    page = new ExpensesFrontendPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
